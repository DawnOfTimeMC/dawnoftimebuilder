package org.dawnoftimebuilder.block;

public interface IBlockSpecialDisplay {

	default float getDisplayScale(){
		return 1.0F;
	};

	//TODO add particules effects
	//void spawnParticules(int meta);
}
