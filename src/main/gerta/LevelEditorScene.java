package main.gerta;

import main.components.SpriteRenderer;
import main.components.Spritesheet;
import main.until.AssetPool;
import org.joml.Vector2f;
/**
 *
 */
public class LevelEditorScene extends Scene {

    public LevelEditorScene(){
    }

    @Override
    public void init () {
        loadResources();

        this.camera = new Camera(new Vector2f(-250, 0));

        Spritesheet sprite = AssetPool.getSpritesheet("assets/images/spritesheet.png");

        GameObject obj1 = new GameObject("Object 1", new Transform(new Vector2f(100, 100), new Vector2f(256, 256)));
        obj1.addComponent(new SpriteRenderer(sprite.getSprite(0)));
        this.addGameObjectToScene(obj1);

        GameObject obj2 = new GameObject("Object 2", new Transform(new Vector2f(400, 100), new Vector2f(256, 256)));
        obj2.addComponent(new SpriteRenderer(sprite.getSprite(1)));
        this.addGameObjectToScene(obj2);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpritesheet("assets/images/spritesheet.png",
                new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"), 16, 16, 26, 0));
    }

    @Override
    public void update(float dt) {
        for (GameObject go : this.gameObjects){
            go.update(dt);
        }

        this.renderer.render();
    }
}
