package main.components;

import main.gerta.Component;

public class FontRenderer extends Component {



    @Override
    public void start(){
        if (gameObject.getComponent(SpriteRenderer.class) != null){
            System.out.println("Fount Font Renderer");
        }
    }

    @Override
    public void update(float dt) {

    }
}
