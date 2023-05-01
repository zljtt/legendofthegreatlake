package github.zljtt.legendofthegreatlake.capabilities;

import net.minecraft.core.BlockPos;

public interface IScheduledEventData {
    BlockPos getEventPosition();

    void setEventPosition(BlockPos pos);

}
