package github.zljtt.legendofthegreatlake.capabilities;

import net.minecraft.core.BlockPos;

public class ScheduledEventData implements IScheduledEventData {

    private BlockPos pos;

    public ScheduledEventData() {
        pos = BlockPos.ZERO;
    }

    @Override
    public BlockPos getEventPosition() {
        return pos;
    }

    @Override
    public void setEventPosition(BlockPos globalPos) {
        this.pos = globalPos;
    }
}
