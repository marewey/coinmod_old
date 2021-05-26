
package net.rewey.markscoins.particle;

import net.rewey.markscoins.MarkscoinsModElements;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.api.distmarker.Dist;

import net.minecraft.particles.ParticleType;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.Minecraft;

@MarkscoinsModElements.ModElement.Tag
public class BankParticle extends MarkscoinsModElements.ModElement {
	public static final BasicParticleType particle = new BasicParticleType(false);
	public BankParticle(MarkscoinsModElements instance) {
		super(instance, 85);
		FMLJavaModLoadingContext.get().getModEventBus().register(this);
	}

	@SubscribeEvent
	public void registerParticleType(RegistryEvent.Register<ParticleType<?>> event) {
		event.getRegistry().register(particle.setRegistryName("bank"));
	}

	@OnlyIn(Dist.CLIENT)
	@SubscribeEvent
	public void registerParticle(ParticleFactoryRegisterEvent event) {
		Minecraft.getInstance().particles.registerFactory(particle, CustomParticleFactory::new);
	}
	@OnlyIn(Dist.CLIENT)
	private static class CustomParticle extends SpriteTexturedParticle {
		private final IAnimatedSprite spriteSet;
		protected CustomParticle(ClientWorld world, double x, double y, double z, double vx, double vy, double vz, IAnimatedSprite spriteSet) {
			super(world, x, y, z);
			this.spriteSet = spriteSet;
			this.setSize((float) 0.2, (float) 0.2);
			this.particleScale *= (float) 1.5;
			this.maxAge = (int) Math.max(1, 8 + (this.rand.nextInt(2) - 1));
			this.particleGravity = (float) 0;
			this.canCollide = true;
			this.motionX = vx * 0;
			this.motionY = vy * 0;
			this.motionZ = vz * 0;
			this.selectSpriteRandomly(spriteSet);
		}

		@Override
		public IParticleRenderType getRenderType() {
			return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
		}

		@Override
		public void tick() {
			super.tick();
		}
	}

	@OnlyIn(Dist.CLIENT)
	private static class CustomParticleFactory implements IParticleFactory<BasicParticleType> {
		private final IAnimatedSprite spriteSet;
		public CustomParticleFactory(IAnimatedSprite spriteSet) {
			this.spriteSet = spriteSet;
		}

		public Particle makeParticle(BasicParticleType typeIn, ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed,
				double zSpeed) {
			return new CustomParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed, this.spriteSet);
		}
	}
}