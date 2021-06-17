package stayabode.foodyHive.models;

import java.util.ArrayList;
import java.util.List;

public class BannerView {

    List<Banners> bannersList = new ArrayList<>();

    public List<Banners> getBannersList() {
        return bannersList;
    }

    public void setBannersList(List<Banners> bannersList) {
        this.bannersList = bannersList;
    }
}
