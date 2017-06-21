package org.oreon.engine.modules.instancing;

import java.util.ArrayList;
import java.util.List;

import org.oreon.engine.engine.buffers.UBO;
import org.oreon.engine.engine.math.Vec3f;
import org.oreon.engine.engine.scenegraph.Node;
import org.oreon.engine.engine.scenegraph.components.TransformsInstanced;

public abstract class InstancingCluster extends Node{
	
	private List<TransformsInstanced> instancingTransforms = new ArrayList<TransformsInstanced>();
	
	private UBO modelMatricesBuffer;
	private UBO worldMatricesBuffer;
	
	private List<Integer> highPolyIndices = new ArrayList<Integer>();
	private List<Integer> lowPolyIndices = new ArrayList<Integer>();
	
	private Vec3f center;
	
	public void updateUBOs(){};
	
	public List<Integer> getHighPolyIndices(){
		return highPolyIndices;
	}
	
	public List<Integer> getLowPolyIndices(){
		return lowPolyIndices;
	}

	public void setHighPolyIndices(List<Integer> highPolyIndices) {
		this.highPolyIndices = highPolyIndices;
	}

	public void setLowPolyIndices(List<Integer> lowPolyIndices) {
		this.lowPolyIndices = lowPolyIndices;
	}

	public List<TransformsInstanced> getInstancingTransforms() {
		return instancingTransforms;
	}

	public void setInstancingTransforms(List<TransformsInstanced> transforms) {
		this.instancingTransforms = transforms;
	}

	public UBO getModelMatricesBuffer() {
		return modelMatricesBuffer;
	}

	public void setModelMatricesBuffer(UBO modelMatricesBuffer) {
		this.modelMatricesBuffer = modelMatricesBuffer;
	}

	public UBO getWorldMatricesBuffer() {
		return worldMatricesBuffer;
	}

	public void setWorldMatricesBuffer(UBO worldMatricesBuffer) {
		this.worldMatricesBuffer = worldMatricesBuffer;
	}

	public Vec3f getCenter() {
		return center;
	}

	public void setCenter(Vec3f center) {
		this.center = center;
	}
}
