package org.oreon.engine.engine.shaders.computing;

import org.oreon.engine.engine.shaders.Shader;
import org.oreon.engine.engine.utils.ResourceLoader;

public class FFTTwiddleFactorsShader extends Shader{

	private static FFTTwiddleFactorsShader instance = null;
	
	public static FFTTwiddleFactorsShader getInstance() 
	{
	    if(instance == null) 
	    {
	    	instance = new FFTTwiddleFactorsShader();
	    }
	      return instance;
	}
	
	protected FFTTwiddleFactorsShader()
	{
		super();
		
		addComputeShader(ResourceLoader.loadShader("shaders/computing/fastFourierTransform/TwiddleFactors.glsl"));
		compileShader();
		
		addUniform("N");
	}
	

	public void updateUniforms(int N)
	{
		setUniformi("N", N);
	}
}
