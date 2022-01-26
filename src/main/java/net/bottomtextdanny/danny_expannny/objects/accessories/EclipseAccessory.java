package net.bottomtextdanny.danny_expannny.objects.accessories;

import net.bottomtextdanny.braincell.mod.opengl_front.point_lighting.SimplePointLight;
import net.bottomtextdanny.braincell.mod.serialization.WorldPacketData;
import net.bottomtextdanny.braincell.mod.serialization.serializers.builtin.BuiltinSerializers;
import net.bottomtextdanny.braincell.mod.world.builtin_sound_instances.StaticSound;
import net.bottomtextdanny.danny_expannny.accessory.StackAccSoftSave;
import net.bottomtextdanny.danny_expannny.object_tables.items.DEItems;
import net.bottomtextdanny.danny_expannny.object_tables.DESounds;
import net.bottomtextdanny.danny_expannny.accessory.AccessoryKey;
import net.bottomtextdanny.braincell.underlying.misc.ObjectFetcher;
import net.bottomtextdanny.danny_expannny.accessory.modules.FinnHit;
import net.bottomtextdanny.dannys_expansion.core.base.pl.ShootLight;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.network.PacketDistributor;
import org.apache.commons.lang3.mutable.MutableObject;

public class EclipseAccessory extends StackAccessory implements StackAccSoftSave, FinnHit {
	public static final int CRIT_LIGHT_CLIENT_CALL = 0;
	public static final int CRITS_BEFORE_ACTS = 3;
	public static final int ECLIPSE_CRITS_MAX = 1;
	public int crits;
	
	public EclipseAccessory(AccessoryKey<?> key, Player player) {
		super(key, player);
	}
	
	@Override
	public void tick() {
		super.tick();
	}

	@Override
	public Item asItem() {
		return DEItems.ECLIPSE.get();
	}

	@Override
	public void onMeleeCritical(LivingEntity entity, MutableObject<Float> amount, float baseValue) {
		if (!this.player.level.isClientSide) {
			if (this.crits >= CRITS_BEFORE_ACTS) {
				int crit = this.crits - CRITS_BEFORE_ACTS;
				if (crit < ECLIPSE_CRITS_MAX) {
					amount.setValue(amount.getValue() * 1.2F);
					triggerClientAction(CRIT_LIGHT_CLIENT_CALL, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> this.player), WorldPacketData.of(BuiltinSerializers.VEC3, entity.getEyePosition()));
				} else {
					this.crits = 0;
				}
			}
			this.crits++;
		}
	}

	@OnlyIn(Dist.CLIENT)
	@Override
	public void accessoryClientManager(int flag, ObjectFetcher fetcher) {
		if (flag == CRIT_LIGHT_CLIENT_CALL) {
			Vec3 targetPos = fetcher.get(0, Vec3.class);
			float crit = 4.5F;
			ShootLight light = new ShootLight(this.player.level, 5, 7, 15);
			Minecraft.getInstance().getSoundManager()
					.play(StaticSound.builder(targetPos, DESounds.IS_ECLIPSE_BLACKHOLE.get(), SoundSource.NEUTRAL)
							.volume(4.0F)
							.pitch(1.0F)
							.distance(10.0F)
							.build());
			light.setLight(new SimplePointLight(new Vec3(-0.8, -1.5, -0.5), crit, 7.0F, 0.0F));
			light.setPosition(targetPos);
			light.addToWorld();
		}
	}

	@Override
	public void keyHandler(Options settings) {
		super.keyHandler(settings);
	}
	
	@Override
	public Object[] save() {
		return new Object[] {this.crits};
	}
	
	@Override
	public void retrieve(ObjectFetcher save) {
		this.crits = save.get(0);
	}

	@Override
	public int critModulePriority() {
		return FinnHit.C_ECLIPSE;
	}

	@Override
	public int hitModulePriority() {
		return FinnHit.H_ADD_OP;
	}
}
