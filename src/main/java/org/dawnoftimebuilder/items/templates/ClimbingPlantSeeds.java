package org.dawnoftimebuilder.items.templates;

import org.dawnoftimebuilder.utils.DoTBBlockStateProperties;

public class ClimbingPlantSeeds extends DoTBItem {

	private DoTBBlockStateProperties.ClimbingPlant climbingPlant;

	public ClimbingPlantSeeds(String name, DoTBBlockStateProperties.ClimbingPlant climbingPlant) {
		super(name);
		this.climbingPlant = climbingPlant;
	}

	public DoTBBlockStateProperties.ClimbingPlant getClimbingPlant(){
		return this.climbingPlant;
	}
}
