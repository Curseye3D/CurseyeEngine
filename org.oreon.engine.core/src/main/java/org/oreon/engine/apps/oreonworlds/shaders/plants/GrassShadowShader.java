package org.oreon.engine.apps.oreonworlds.shaders.plants;

import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;

import java.util.List;

import org.oreon.engine.engine.scenegraph.GameObject;
import org.oreon.engine.engine.scenegraph.components.Material;
import org.oreon.engine.engine.shaders.Shader;
import org.oreon.engine.engine.utils.Constants;
import org.oreon.engine.engine.utils.ResourceLoader;
import org.oreon.engine.modules.instancing.InstancingCluster;

public class GrassShadowShader extends Shader{

	private static GrassShadowShader instance;

	public static GrassShadowShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new GrassShadowShader();
	    }
	     return instance;
	}
	
	protected GrassShadowShader()
	{
		super();
		
		addVertexShader(ResourceLoader.loadShader("oreonworlds/shaders/Grass_Shader/GrassShadow_VS.glsl"));
		addGeometryShader(ResourceLoader.loadShader("oreonworlds/shaders/Grass_Shader/GrassShadow_GS.glsl"));
		addFragmentShader(ResourceLoader.loadShader("oreonworlds/shaders/Grass_Shader/GrassShadow_FS.glsl"));
		compileShader();
		
		addUniformBlock("worldMatrices");
		addUniformBlock("Camera");
		addUniformBlock("LightViewProjections");
		addUniform("material.diffusemap");
		
		for (int i=0; i<500; i++)
		{
			addUniform("matrixIndices[" + i + "]");
		}
	}
	
	public void updateUniforms(GameObject object){
		
		bindUniformBlock("Camera",Constants.CameraUniformBlockBinding);
		bindUniformBlock("LightViewProjections",Constants.LightMatricesUniformBlockBinding);
		((InstancingCluster) object.getParent()).getWorldMatricesBuffer().bindBufferBase(0);
		bindUniformBlock("worldMatrices", 0);
		
		Material material = (Material) object.getComponent("Material");

		glActiveTexture(GL_TEXTURE0);
		material.getDiffusemap().bind();
		setUniformi("material.diffusemap", 0);
		
		List<Integer> indices = ((InstancingCluster) object.getParent()).getHighPolyIndices();
		
		for (int i=0; i<indices.size(); i++)
		{
			setUniformi("matrixIndices[" + i +"]", indices.get(i));	
		}
	}

}
