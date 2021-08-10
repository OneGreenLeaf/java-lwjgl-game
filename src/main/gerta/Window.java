package main.gerta;

import main.until.Time;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    /**
     *
     */
    private final int width;
    private final int height;
    private final String title;
    private long glfwWindow;

    public float r, g, b, a;

    private static Window window;

    private static  Scene currentScene;

    static {
        window = null;
    }

    private Window() {
        this.width = 800;
        this.height = 600;
        this.title = "Window";
        r = 1;
        b = 1;
        g = 1;
        a = 1;
    }

    public static void changeScene(int newScene){
        switch (newScene){
            case 0:
                currentScene = new LevelEditorScene();
                currentScene.init();
                currentScene.start();
                break;
            case 1:
                currentScene = new LevelScene();
                currentScene.init();
                currentScene.start();
                break;
            default:
                assert false: "Unknown scene '" + newScene + "'";
                break;
        }
    }

    public static  Window get(){
        if (Window.window == null){
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run () {
        System.out.println("Hello LWJGL" + Version.getVersion()+ "!");

        init();
        loop();

        /*
         Free the memory
         */
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        /*
          Terminate GLFW and the free the error callback
         */
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    /*
      Setup and error callback
     */
    public void init(){
        GLFWErrorCallback.createPrint(System.err).set();

        /*
         Initialize GLFW
         */
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW.");
        }
        /*
         Configure GLFW
         */
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        /*
          Create the window
         */
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if(glfwWindow == NULL){
            throw new IllegalStateException("Failed to create the GLFW window.");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallback);

        /*
          Make the OpenGL context current
          glfwSwapInterval(1) turn On v-sync
         */
        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1);

        /*
          Make the window visible
         */
        glfwShowWindow(glfwWindow);

        /*
          This line is critical for LWJGL's interoperation with GLFW's
          OpenGL context, or any context that is managed externally.
          LWJGL detects the context that is current in the current thread,
          creates the GLCapabilities instance and makes the OpenGL
          bindings available for use.
         */
        GL.createCapabilities();

        Window.changeScene(0);
    }

    public void loop(){
        float beginTime = Time.getTime();
        float endTime;
        float dt = -1.0f;

        while (!glfwWindowShouldClose(glfwWindow)){
            //Poll events
            glfwPollEvents();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt >= 0) {
                currentScene.update(dt);
            }

            if (KeyListener.isKeyPressed(GLFW_KEY_ESCAPE)){
                glfwSetWindowShouldClose(glfwWindow, true);
            }

            glfwSwapBuffers(glfwWindow);

            endTime = Time.getTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
    }
}
