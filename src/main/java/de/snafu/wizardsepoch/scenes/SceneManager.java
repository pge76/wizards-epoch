package de.snafu.wizardsepoch.scenes;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;

import static de.snafu.wizardsepoch.scenes.SceneType.*;


@Log4j2
public class SceneManager {
    private static final HashMap<SceneType, Scene> sceneMap = new HashMap<>();

    @Getter
    private static Scene currentScene;

    static {
        sceneMap.put(BATTLEMAP, new BattleMapScene());
        sceneMap.put(LEVELEDITOR, new LevelEditorScene());
        sceneMap.put(OVERWORLD, new OverworldScene());
        currentScene = sceneMap.get(LEVELEDITOR);
    }

    public static void changeToScene(SceneType sceneType) {
        currentScene = getScene(sceneType);
        log.info("changed to scene {}", sceneType.toString());
    }

    public static Scene getScene(SceneType sceneType) {
        return sceneMap.get(sceneType);
    }


}
