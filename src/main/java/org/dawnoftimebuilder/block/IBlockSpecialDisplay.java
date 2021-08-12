package org.dawnoftimebuilder.block;

public interface IBlockSpecialDisplay {

	default float getDisplayScale(){
		return 1.0F;
	};

	default int getDisplayedLightLevel(){
		return 0;
	}

	default void getDisplayedParticles(){}
}
