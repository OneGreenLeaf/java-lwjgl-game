package main.gerta;

/**
 * This class for abstract method with name update for update delta time for FPS
 */
public abstract class Scene {

    protected Camera camera;

    public Scene(){

    }

    public void init(){

    }

    public abstract void update(float dt);
}
