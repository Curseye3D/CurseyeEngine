package org.oreon.engine.engine.scenegraph;

import org.oreon.engine.engine.scenegraph.components.Transform;

public class Scenegraph extends Node{
	
	private Node rootObject;
	private Node terrain;
	private Node water;
	
	private boolean terrainExists = false;
	
	public Scenegraph(){
		
		setTransform(new Transform());
		rootObject = new Node();
		terrain = new Node();
		water = new Node();
		rootObject.setParent(this);
		terrain.setParent(this);
		water.setParent(this);
	}
	
	public void render(){
		rootObject.render();
		terrain.render();
		water.render();
	}
	
	public void renderShadows(){
		rootObject.renderShadows();
		terrain.renderShadows();
		water.renderShadows();
	}
	
	public void update(){
		rootObject.update();
		terrain.update();
		water.update();
	}
	
	public void input(){
		rootObject.input();
		terrain.input();
		water.input();
	}
	
	public void shutdown()
	{
		rootObject.shutdown();
		terrain.shutdown();
		water.shutdown();
	}

	public Node getRoot() {
		return rootObject;
	}
	
	public void addObject(Node object){
		rootObject.addChild(object);
	}

	public Node getTerrain() {
		return terrain;
	}

	public void setTerrain(Node terrain) {
		terrain.setParent(this);
		setTerrainExists(true);
		this.terrain = terrain;
	}

	public Node getWater() {
		return water;
	}

	public void setWater(Node water) {
		water.setParent(this);
		this.water = water;
	}

	public boolean terrainExists() {
		return terrainExists;
	}

	public void setTerrainExists(boolean terrainExists) {
		this.terrainExists = terrainExists;
	}

}
