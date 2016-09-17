package net.bplaced.therefactory.jewelthief.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import net.bplaced.therefactory.jewelthief.constants.Config;
import net.bplaced.therefactory.jewelthief.misc.Util;

/**
 * Created by Christian on 10.09.2016.
 */
public class Particles {

    private ParticleEffectPool fireworksEffectPool;
    private ParticleEffectPool.PooledEffect[] fireworkEffects;
    private float[][] startColors; // start colors to choose from for the particle effects

    public Particles(TextureAtlas textureAtlas) {
        fireworkEffects = new ParticleEffectPool.PooledEffect[5];
        startColors = new float[][]{ //
                new float[]{0, .58f, 1}, // blue
                new float[]{1, .984f, .267f}, // yellow
                new float[]{.969f, .11f, 1}, // pink
                new float[]{1, .02f, .082f}, // red
                new float[]{.816f, 0, 1}, // violet
                new float[]{0, 1, .098f}, // green
        };

        ParticleEffect fireworksEffect = new ParticleEffect();
        fireworksEffect.load(Gdx.files.internal("particles/fireworks.p"), textureAtlas);

        // if particle effect includes additive or pre-multiplied particle emitters
        // you can turn off blend function clean-up to save a lot of draw calls
        // but remember to switch the Batch back to GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA
        // before drawing "regular" sprites or your Stage.
        fireworksEffect.setEmittersCleanUpBlendFunction(false);

        fireworksEffectPool = new ParticleEffectPool(fireworksEffect, 1, 5);
        for (int i = 0; i < fireworkEffects.length; i++) {
            ParticleEffectPool.PooledEffect effect = fireworksEffectPool.obtain();
            resetFireworksEffect(effect);
            fireworkEffects[i] = effect;
        }
    }

    /**
     * if particle effect includes additive or pre-multiplied particle emitters
     * you can turn off blend function clean-up to save a lot of draw calls
     * but remember to switch the Batch back to GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA
     * before drawing "regular" sprites or your Stage.
     *
     * @param batch
     */
    private void resetBlendFunction(SpriteBatch batch) {
        batch.setBlendFunction(-1, -1);
        Gdx.gl20.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_DST_ALPHA);
    }

    /**
     * Renders the fireworks effects on the given batch and resets each effect if it has reached its end.
     *
     * @param batch
     * @param delta
     */
    public void renderFireworks(SpriteBatch batch, float delta) {
        for (ParticleEffectPool.PooledEffect effect : fireworkEffects) {
            effect.draw(batch, delta);
            if (effect.isComplete()) {
                resetFireworksEffect(effect);
            }
        }
        resetBlendFunction(batch);
    }


    /**
     * Resets the position, start color and duration of all firework effects to random values.
     */
    public void resetFireworksEffects() {
        for (ParticleEffectPool.PooledEffect effect : fireworkEffects) {
            resetFireworksEffect(effect);
        }
    }

    /**
     * Resets the position, start color and duration of the given firework effects to random values.
     *
     * @param effect
     */
    public void resetFireworksEffect(ParticleEffect effect) {
        effect.reset();
        effect.setDuration(Util.randomWithin(180, 250));
        effect.setPosition(Util.randomWithin(0, Config.WINDOW_WIDTH), Util.randomWithin(0, Config.WINDOW_HEIGHT));
        float[] colors = effect.getEmitters().get(0).getTint().getColors();
        int randomStartColor = Util.randomWithin(0, startColors.length - 1);
        for (int i = 0; i < 6; i++) {
            colors[i] = startColors[randomStartColor][i % 3];
        }
        for (ParticleEmitter emitter : effect.getEmitters()) {
            emitter.getTint().setColors(colors);
        }
    }

    /**
     * Frees all allocated resources.
     */
    public void dispose() {
        for (ParticleEffectPool.PooledEffect effect : fireworkEffects) {
            effect.free();
            effect.dispose();
        }
        fireworksEffectPool.clear();
    }

}