#if MC_VERSION < 260300
package io.mikip98.humilityval.client.extensions.com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.PoseStack;
import manifold.ext.rt.api.Extension;
import manifold.ext.rt.api.This;
import org.joml.Quaternionf;

@Extension
public class PoseStackExt {
    public static void rotate(@This PoseStack poseStack, final Quaternionf by) {
        poseStack.mulPose(by);
    }
}
#endif