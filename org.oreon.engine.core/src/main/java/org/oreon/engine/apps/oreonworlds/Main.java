package org.oreon.engine.apps.oreonworlds;

import org.oreon.engine.apps.oreonworlds.assets.plants.Bush01ClusterGroup;
import org.oreon.engine.apps.oreonworlds.assets.plants.Grass01ClusterGroup;
import org.oreon.engine.apps.oreonworlds.assets.plants.Palm01ClusterGroup;
import org.oreon.engine.apps.oreonworlds.assets.plants.Plant01ClusterGroup;
import org.oreon.engine.apps.oreonworlds.assets.plants.Tree01ClusterGroup;
import org.oreon.engine.apps.oreonworlds.assets.plants.Tree02ClusterGroup;
import org.oreon.engine.apps.oreonworlds.assets.rocks.Rock01ClusterGroup;
import org.oreon.engine.apps.oreonworlds.assets.rocks.Rock02ClusterGroup;
import org.oreon.engine.apps.oreonworlds.gui.GUI;
import org.oreon.engine.apps.oreonworlds.shaders.terrain.TerrainGridShader;
import org.oreon.engine.apps.oreonworlds.shaders.terrain.TerrainShader;
import org.oreon.engine.apps.oreonworlds.water.Ocean;
import org.oreon.engine.apps.worldgenerator.tools.terrainEditor.TerrainShadowShader;
import org.oreon.engine.engine.core.Game;
import org.oreon.engine.modules.atmosphere.SkySphere;
import org.oreon.engine.modules.atmosphere.Sun;
import org.oreon.engine.modules.terrain.Terrain;

public class Main {

	public static void main(String[] args) {
		
		Game game = new Game();
		game.setGui(new GUI());
		game.getEngine().createWindow(1280, 720, "oreon worlds");
		game.init();
		game.getScenegraph().setTerrain(Terrain.getInstance());
		Terrain.getInstance().init("./res/oreonworlds/terrain/terrain_settings.txt",
								   "./res/oreonworlds/terrain/terrain_settings_LowPoly.txt",
								   TerrainShader.getInstance(),
								   TerrainGridShader.getInstance(), 
								   TerrainShadowShader.getInstance());
		game.getScenegraph().addObject(new SkySphere());	
		game.getScenegraph().addObject(new Sun());
		game.getScenegraph().getRoot().addChild(new Palm01ClusterGroup());
		game.getScenegraph().getRoot().addChild(new Plant01ClusterGroup());
		game.getScenegraph().getRoot().addChild(new Grass01ClusterGroup());
		game.getScenegraph().getRoot().addChild(new Tree01ClusterGroup());
		game.getScenegraph().getRoot().addChild(new Tree02ClusterGroup());
		game.getScenegraph().getRoot().addChild(new Rock01ClusterGroup());
		game.getScenegraph().getRoot().addChild(new Rock02ClusterGroup());
		game.getScenegraph().getRoot().addChild(new Bush01ClusterGroup());
		game.getScenegraph().setWater(new Ocean());
		game.launch();
	}

}
