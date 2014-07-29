package net.bymarcin.evenmoreutilities.handler;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.FillBucketEvent;

public class BucketHandler {
    public static BucketHandler INSTANCE = new BucketHandler();
    public Map<Integer, Item> buckets = new HashMap<Integer, Item>();

    private BucketHandler() {
    }

    
    @ForgeSubscribe
    public void onBucketFill(FillBucketEvent event) {
            ItemStack result = fillCustomBucket(event.world, event.target);

            if (result == null)
                    return;

            event.result = result;
            event.setResult(Result.ALLOW);
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {

            int id = world.getBlockId(pos.blockX, pos.blockY, pos.blockZ);

            Item bucket = buckets.get(id);
            if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
                    world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
                    return new ItemStack(bucket);
            } else
                    return null;

    }
}
