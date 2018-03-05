package fr.olbati.owish.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import fr.olbati.owish.bean.WishReadBean;
import fr.olbati.owish.bean.WishWriteBean;

import java.net.UnknownHostException;
import java.util.List;

public interface WishService {

    public List<WishReadBean> getAll() throws UnknownHostException;
    public WishReadBean add(WishWriteBean wishWriteBean);
    public WishReadBean getById(Long id);
    public WishReadBean edit(Long id, WishWriteBean wishWriteBean);
    public void remove(Long id);

}
