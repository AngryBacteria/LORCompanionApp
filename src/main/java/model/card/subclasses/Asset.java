package model.card.subclasses;

import java.util.Objects;

/**
 * Assets are 2 pictures, one is the Full sized art, the other one is the smaller Version that is used in the Game.
 * The smaller Version has additional info on it such as the description text etc.
 */
public class Asset {

    private String gameAbsolutePath;
    private String fullAbsolutePath;

    public Asset(String gameAbsolutePath, String fullAbsolutePath) {
        this.gameAbsolutePath = gameAbsolutePath.replace("http://", "https://");
        this.fullAbsolutePath = fullAbsolutePath.replace("http://", "https://");
    }

    public String getGameAbsolutePath() {
        return this.gameAbsolutePath.replace("http://", "https://");
    }

    public String getFullAbsolutePath() {
        return this.fullAbsolutePath.replace("http://", "https://");
    }

    public void setGameAbsolutePath(String gameAbsolutePath) {
        this.gameAbsolutePath = gameAbsolutePath;
    }

    public void setFullAbsolutePath(String fullAbsolutePath) {
        this.fullAbsolutePath = fullAbsolutePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return Objects.equals(gameAbsolutePath, asset.gameAbsolutePath) && Objects.equals(fullAbsolutePath, asset.fullAbsolutePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameAbsolutePath, fullAbsolutePath);
    }

    @Override
    public String toString() {
        return "Asset{" +
                "gameAbsolutePath='" + getGameAbsolutePath() + '\'' +
                ", fullAbsolutePath='" + getFullAbsolutePath() + '\'' +
                '}';
    }
}
