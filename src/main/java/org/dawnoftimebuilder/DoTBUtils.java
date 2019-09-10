package org.dawnoftimebuilder;

import java.util.List;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;

public class DoTBUtils {

    /**
     * Helper method that returns a new {@link AxisAlignedBB} rotated for a given {@link EnumFacing}
     * using an AxisAlignedBB oriented toward North
     */
	public static AxisAlignedBB getRotatedAABB(AxisAlignedBB AABB, EnumFacing facing) {
		
		if (facing == EnumFacing.UP||facing == EnumFacing.DOWN||facing == EnumFacing.NORTH) return AABB;
		else {
			
			double XA;
			double ZA;
			double XB;
			double ZB;
			double x;
			double z;
			
	        switch (facing) {
	        	default:
	            case EAST:
	                XA = 1.0D - AABB.minZ;
	                ZA = AABB.minX;
	                x = 1.0D - AABB.maxZ;
	                z = AABB.maxX;
	                break;
	            case SOUTH:
	                XA = 1.0D - AABB.minX;
	                ZA = 1.0D - AABB.minZ;
	                x = 1.0D - AABB.maxX;
	                z = 1.0D - AABB.maxZ;
	                break;
	            case WEST:
	                XA = AABB.minZ;
	                ZA = 1.0D - AABB.minX;
	                x = AABB.maxZ;
	                z = 1.0D - AABB.maxX;
	                break;
	        }
	        
            if (x >= XA) XB = x;
            else {
            	XB = XA;
            	XA = x;
            }
            if (z >= ZA) ZB = z;
            else {
            	ZB = ZA;
            	ZA = z;
            }
			return new AxisAlignedBB(XA, AABB.minY, ZA, XB, AABB.maxY, ZB);
		}
	}
	
    /**
     * Helper method that returns the smallest {@link AxisAlignedBB} that can hold every 
     * AxisAlignedBB from a List
     */
	public static AxisAlignedBB getMainAABB(List<AxisAlignedBB> list) {
		if (list.size() < 1) return null;
		else {
			AxisAlignedBB AABB = list.get(0);
			if (list.size() > 1) {
				for (AxisAlignedBB aabb : list) {
					AABB = AABB.union(aabb);
				}
			}
			return AABB;
		}
	}
}
