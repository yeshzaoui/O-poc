package fr.olbati.owish.mapper;

import fr.olbati.owish.bean.WishReadBean;
import fr.olbati.owish.bean.WishWriteBean;
import fr.olbati.owish.entity.Wish;

import java.util.ArrayList;
import java.util.List;

public class WishMapper {

    public static WishReadBean toBean(Wish wish) {
        WishReadBean wishReadBean = new WishReadBean();
        wishReadBean.setId(wish.getId());
        wishReadBean.setTitle(wish.getTitle());
        wishReadBean.setContent(wish.getContent());
        wishReadBean.setCreatedAt(wish.getCreatedAt().toString());
        wishReadBean.setUpdatedAt(wish.getUpdatedAt().toString());
        return wishReadBean;
    }

    public static Wish toEntity(WishWriteBean wishWriteBean) {
        Wish wish = new Wish();
        wish.setTitle(wishWriteBean.getTitle());
        wish.setContent(wishWriteBean.getContent());
        return wish;
    }

    public static List<WishReadBean> toBeans (List<Wish> wishes) {
        List<WishReadBean> wishReadBeans = new ArrayList<>();
        for (Wish wish : wishes) {
            wishReadBeans.add(WishMapper.toBean(wish));
        }
        return wishReadBeans;
    }

}
