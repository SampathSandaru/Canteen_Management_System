package com.fot.Canteen_Management_System.Controller;

import com.fot.Canteen_Management_System.Entity.Item;
import com.fot.Canteen_Management_System.Entity.User;
import com.fot.Canteen_Management_System.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping(path = "/save_item",method = RequestMethod.POST)
    public String save_item(@ModelAttribute("Item")Item item,@RequestParam("file") MultipartFile file){

        Path resourceDirectory = Paths.get("src","main","resources","static","Admin","img","item","item_");
        String absolutePath = resourceDirectory.toFile().getAbsolutePath();
        try {
            file.transferTo(new File(absolutePath + file.getOriginalFilename()));
        }catch (Exception e){

        }
        item.setImg_path(file.getOriginalFilename());
        System.out.println(item.getItem_id());
        itemService.save(item);
        return "redirect:/canteenmanager_item";
    }

    @RequestMapping(path = "/delete_item/{id}/{imgname}")
    public String delete(@PathVariable("id")Integer id,@PathVariable("imgname")String imgname){
        Path resourceDirectory = Paths.get("src","main","resources","static","Admin","img","item","item_");

        itemService.delete(id);

//        File delete_img=new File(resourceDirectory+imgname);
//        try {
//            delete_img.delete();
//        }catch (Exception e){
//
//        }

        return "redirect:/canteenmanager_item";
    }

    @RequestMapping(path = "/update_item")
    public String update_item(@RequestParam("item_id")Integer id,@ModelAttribute("item")Item item){
        itemService.update(id,item);
        return "redirect:/canteenmanager_item";
    }



}
