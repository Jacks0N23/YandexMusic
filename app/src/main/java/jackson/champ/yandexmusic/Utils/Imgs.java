package jackson.champ.yandexmusic.Utils;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jackson on 31.03.16.
 */
public class Imgs  extends Artist{

    @SerializedName("small")
    public String small;
    @SerializedName("big")
    public String big;

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }


}
