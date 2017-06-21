package org.oreon.engine.engine.scenegraph.components;

import org.oreon.engine.engine.configs.RenderConfig;
import org.oreon.engine.engine.shaders.Shader;

public class RenderInfo {
	
	private RenderConfig config;
	private Shader shader;
	private Shader shadowShader;
	private boolean shadowCaster;
	
	public RenderInfo(RenderConfig config, Shader shader){
		this.config = config;
		this.shader = shader;
		this.shadowCaster = false;
	}
	
	public RenderInfo(RenderConfig config, Shader shader, Shader shadowShader){
		this.config = config;
		this.shader = shader;
		this.shadowShader = shadowShader;
		this.shadowCaster = true;
	}
	
	public RenderConfig getConfig() {
		return config;
	}
	public void setConfig(RenderConfig config) {
		this.config = config;
	}
	public Shader getShader() {
		return shader;
	}
	public void setShader(Shader shader) {
		this.shader = shader;
	}
	public Shader getShadowShader() {
		return shadowShader;
	}
	public void setShadowShader(Shader shadowShader) {
		this.shadowShader = shadowShader;
		this.shadowCaster = true;
	}

	public boolean isShadowCaster() {
		return shadowCaster;
	}
}
