#if POLYMER
package io.mikip98.humilityval.content.block.entity.polymer;

import eu.pb4.polymer.virtualentity.api.ElementHolder;
import eu.pb4.polymer.virtualentity.api.attachment.ChunkAttachment;
import eu.pb4.polymer.virtualentity.api.attachment.HolderAttachment;
import eu.pb4.polymer.virtualentity.api.elements.ItemDisplayElement;
import io.mikip98.humilityval.content.block.entity.AVLBlockEntity;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class PolymerBlockEntity extends BlockEntity {
    protected static final Queue<Runnable> ATTACHMENT_QUEUE = new ConcurrentLinkedQueue<>();
    private static final int perTickLimit = Integer.getInteger("humilityval.polymer.attachment_per_tick_limit", 500);

    static {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            Runnable task;
            int processed = 0;
            // Process up to 'perTickLimit' attachments per tick to prevent server lag spikes
            while (processed < perTickLimit && (task = ATTACHMENT_QUEUE.poll()) != null) {
                task.run();
                processed++;
            }
        });
    }

    protected final AVLBlockEntity.PolymerProperties properties;
    protected final ElementHolder holder = new ElementHolder();
    protected final ItemDisplayElement display = new ItemDisplayElement();
    protected HolderAttachment attachment;

    public PolymerBlockEntity(
            BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState
            #if POLYMER, AVLBlockEntity.PolymerProperties properties #endif
    ) {
        super(blockEntityType, blockPos, blockState);

        this.properties = properties;
        this.holder.addElement(display);
        display.setItemDisplayContext(ItemDisplayContext.FIXED);
        display.setItem(blockState.getBlock().asItem().getDefaultInstance());
        if (this.properties.offset) {
            final Vector3f offset = this.properties.offsetDirection.step().mul(-0.51f);
            display.setTranslation(offset);
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.attachment != null) {
            this.attachment.destroy();
            this.attachment = null;
        }
    }

    @Override
    public void clearRemoved() {
        super.clearRemoved();
        if (this.level instanceof ServerLevel serverLevel) {
            Vec3 offsetPos = Vec3.atCenterOf(this.worldPosition);

            if (this.properties.offset) {
                final Vector3f offset = this.properties.offsetDirection.step().mul(0.51f);
                offsetPos = offsetPos.add(new Vec3(offset));
            }
            final Vec3 finalOffsetPos = offsetPos;

            ATTACHMENT_QUEUE.add(() -> {
                if (this.isRemoved()) return;
                final ChunkAttachmentBuilder attachmentStrategy = this.properties.ticking ? ChunkAttachment::ofTicking : ChunkAttachment::of;
                this.attachment = attachmentStrategy.apply(this.holder, serverLevel, finalOffsetPos);
            });
        }
    }

    @FunctionalInterface
    public interface ChunkAttachmentBuilder {
        HolderAttachment apply(ElementHolder holder, ServerLevel serverLevel, Vec3 offsetPos);
    }

    public static class PolymerProperties {
        protected boolean ticking = false;
        protected boolean offset = false;
        protected @NotNull Direction offsetDirection = Direction.UP;

        public PolymerProperties() {}

        public static PolymerProperties create() {
            return new PolymerProperties();
        }

        public static PolymerProperties of() {
            return new PolymerProperties();
        }

        public PolymerProperties ticking() {
            this.ticking = true;
            return this;
        }

        public PolymerProperties offset() {
            this.offset = true;
            return this;
        }

        public PolymerProperties offset(Direction offsetDirection) {
            this.offset = true;
            this.offsetDirection = offsetDirection;
            return this;
        }
    }
}
#endif