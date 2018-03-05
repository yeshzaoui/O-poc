package fr.olbati.owish.controller;

import fr.olbati.owish.bean.WishReadBean;
import fr.olbati.owish.bean.WishWriteBean;
import fr.olbati.owish.service.WishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import java.net.UnknownHostException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/wishes")
public class WishController {

    @Autowired
    private WishService wishService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<WishReadBean>> getAll() {
        List<WishReadBean> wishReadBeans = null;
        try {
            wishReadBeans = wishService.getAll();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(wishReadBeans, HttpStatus.OK);
    }

    @MessageMapping("/wishes")
    @SendTo("/topic/wishes")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<WishReadBean> add(@RequestBody WishWriteBean wishWriteBean) {
        WishReadBean wishReadBean = wishService.add(wishWriteBean);
        return new ResponseEntity<>(wishReadBean, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_CLIENT')")
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public ResponseEntity<WishReadBean> getById(@PathVariable("id") Long id) {
        WishReadBean wishReadBean = wishService.getById(id);
        return new ResponseEntity<>(wishReadBean, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public ResponseEntity<WishReadBean> edit(@PathVariable("id") Long id, @RequestBody WishWriteBean wishWriteBean) {
        WishReadBean wishReadBean = wishService.edit(id, wishWriteBean);
        return new ResponseEntity<>(wishReadBean, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity remove(@PathVariable("id") Long id) {
        wishService.remove(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
