package org.oreon.engine.apps.oreonworlds.assets.plants;

import java.nio.FloatBuffer;
import java.util.List;

import org.oreon.engine.apps.oreonworlds.shaders.InstancingGridShader;
import org.oreon.engine.apps.oreonworlds.shaders.plants.PalmBillboardShader;
import org.oreon.engine.apps.oreonworlds.shaders.plants.PalmShader;
import org.oreon.engine.engine.buffers.MeshVAO;
import org.oreon.engine.engine.buffers.UBO;
import org.oreon.engine.engine.core.Camera;
import org.oreon.engine.engine.core.RenderingEngine;
import org.oreon.engine.engine.math.Vec3f;
import org.oreon.engine.engine.scenegraph.GameObject;
import org.oreon.engine.engine.scenegraph.Node;
import org.oreon.engine.engine.scenegraph.components.Renderer;
import org.oreon.engine.engine.scenegraph.components.TransformsInstanced;
import org.oreon.engine.engine.utils.BufferUtil;
import org.oreon.engine.modules.instancing.InstancedDataObject;
import org.oreon.engine.modules.instancing.InstancingCluster;
import org.oreon.engine.modules.terrain.Terrain;

public class Palm01Cluster extends InstancingCluster{

	public Palm01Cluster(int instances, Vec3f pos,  List<InstancedDataObject> objects){
		
		setCenter(pos);
		int buffersize = Float.BYTES * 16 * instances;
				
		for (int i=0; i<instances; i++){
			Vec3f translation = new Vec3f((float)(Math.random()*100)-50 + getCenter().getX(), 0, (float)(Math.random()*100)-50 + getCenter().getZ());
			float terrainHeight = Terrain.getInstance().getTerrainHeight(translation.getX(),translation.getZ());
			terrainHeight -= 3;
			translation.setY(terrainHeight);
			float s = (float)(Math.random()*0.15 + 0.2);
			Vec3f scaling = new Vec3f(s,s,s);
			Vec3f rotation = new Vec3f(0,(float) Math.random()*360f,0);
			
			TransformsInstanced transform = new TransformsInstanced();
			transform.setTranslation(translation);
			transform.setScaling(scaling);
			transform.setRotation(rotation);
			transform.setLocalRotation(rotation);
			transform.initMatrices();
			getInstancingTransforms().add(transform);
			getLowPolyIndices().add(i);
		}
		
		setModelMatricesBuffer(new UBO());
		getModelMatricesBuffer().allocate(buffersize);
		
		setWorldMatricesBuffer(new UBO());
		getWorldMatricesBuffer().allocate(buffersize);	
		
		/**
		 * init matrices UBO's
		 */
		int size = Float.BYTES * 16 * instances;
		
		FloatBuffer worldMatricesFloatBuffer = BufferUtil.createFloatBuffer(size);
		FloatBuffer modelMatricesFloatBuffer = BufferUtil.createFloatBuffer(size);
		
		for(TransformsInstanced matrix : getInstancingTransforms()){
			worldMatricesFloatBuffer.put(BufferUtil.createFlippedBuffer(matrix.getWorldMatrix()));
			modelMatricesFloatBuffer.put(BufferUtil.createFlippedBuffer(matrix.getModelMatrix()));
		}
		
		getWorldMatricesBuffer().updateData(worldMatricesFloatBuffer, size);
		getModelMatricesBuffer().updateData(modelMatricesFloatBuffer, size);
		
		for (InstancedDataObject dataObject : objects){
			GameObject object = new GameObject();
			MeshVAO vao = new MeshVAO((MeshVAO) dataObject.getVao());
			vao.setInstances(instances);
			Renderer renderer = new Renderer(dataObject.getRenderInfo().getShader(), vao);
			object.setRenderInfo(dataObject.getRenderInfo());
			object.addComponent("Material", dataObject.getMaterial());
			object.addComponent("Renderer", renderer);
			addChild(object);
		}
		
		updateUBOs();
	}

	public void update()
	{	
		super.update();
		
		if (getCenter().sub(Camera.getInstance().getPosition()).length() < 800){
			
			updateUBOs();
		}
		else if(getHighPolyIndices().size() > 0){
			System.out.println(getCenter().sub(Camera.getInstance().getPosition()).length());
			System.out.println(getHighPolyIndices().size());
		}
		
		if (RenderingEngine.isGrid()){
			for (Node child : getChildren()){
				((GameObject) child).getRenderInfo().setShader(InstancingGridShader.getInstance());
			}
		}
		else{
			((GameObject) getChildren().get(0)).getRenderInfo().setShader(PalmShader.getInstance());
			((GameObject) getChildren().get(1)).getRenderInfo().setShader(PalmShader.getInstance());
			((GameObject) getChildren().get(2)).getRenderInfo().setShader(PalmShader.getInstance());
			((GameObject) getChildren().get(3)).getRenderInfo().setShader(PalmShader.getInstance());
			((GameObject) getChildren().get(4)).getRenderInfo().setShader(PalmBillboardShader.getInstance());
		}
	}
	
	public void updateUBOs(){
		
		getHighPolyIndices().clear();
		
		int index = 0;
		
		for (TransformsInstanced transform : getInstancingTransforms()){
			if (transform.getTranslation().sub(Camera.getInstance().getPosition()).length() < 400){
				getHighPolyIndices().add(index);
			}

			index++;
		}
		
		((MeshVAO) ((Renderer) ((GameObject) getChildren().get(0)).getComponent("Renderer")).getVao()).setInstances(getHighPolyIndices().size());
		((MeshVAO) ((Renderer) ((GameObject) getChildren().get(1)).getComponent("Renderer")).getVao()).setInstances(getHighPolyIndices().size());
		((MeshVAO) ((Renderer) ((GameObject) getChildren().get(2)).getComponent("Renderer")).getVao()).setInstances(getHighPolyIndices().size());
		((MeshVAO) ((Renderer) ((GameObject) getChildren().get(3)).getComponent("Renderer")).getVao()).setInstances(getHighPolyIndices().size());
		
		((MeshVAO) ((Renderer) ((GameObject) getChildren().get(4)).getComponent("Renderer")).getVao()).setInstances(getLowPolyIndices().size());
	}
	
	public void render(){
		if (!RenderingEngine.isWaterReflection() && !RenderingEngine.isWaterRefraction()){
			super.render();
		}
	}
}
