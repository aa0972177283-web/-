package com.fluffyhamsters;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.animal.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;

public class HamsterEntity extends TamableAnimal {
    public HamsterEntity(EntityType<? extends HamsterEntity> type, Level level) { super(type, level); }
    public static AttributeSupplier.Builder createAttributes() { return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 8).add(Attributes.MOVEMENT_SPEED, 0.23).add(Attributes.FOLLOW_RANGE, 16); }
    @Override protected void registerGoals() {
        goalSelector.addGoal(0, new FloatGoal(this));
        goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(2, new FollowOwnerGoal(this, 1.1, 3.0f, 1.5f, false));
        goalSelector.addGoal(3, new TemptGoal(this, 1.15, Ingredient.of(Items.WHEAT_SEEDS, Items.PUMPKIN_SEEDS, Items.MELON_SEEDS), false));
        goalSelector.addGoal(4, new RandomStrollGoal(this, 0.8));
        goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, 6.0f));
        goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }
    @Override public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        boolean seed = stack.is(Items.WHEAT_SEEDS) || stack.is(Items.PUMPKIN_SEEDS) || stack.is(Items.MELON_SEEDS);
        if (seed) {
            if (!level().isClientSide) {
                if (!player.getAbilities().instabuild) stack.shrink(1);
                if (!isTame()) {
                    if (random.nextInt(3) == 0) { tame(player); setOrderedToSit(true); level().broadcastEntityEvent(this, (byte)7); }
                    else level().broadcastEntityEvent(this, (byte)6);
                } else if (getHealth() < getMaxHealth()) heal(2.0f);
            }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        if (isTame() && isOwnedBy(player)) {
            if (!level().isClientSide) { setOrderedToSit(!isOrderedToSit()); navigation.stop(); }
            return InteractionResult.sidedSuccess(level().isClientSide);
        }
        return super.mobInteract(player, hand);
    }
    @Override public boolean isFood(ItemStack stack) { return stack.is(Items.WHEAT_SEEDS) || stack.is(Items.PUMPKIN_SEEDS) || stack.is(Items.MELON_SEEDS); }
    @Override public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob parent) { return FluffyHamsters.HAMSTER.get().create(level); }
}
