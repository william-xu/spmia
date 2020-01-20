package org.xwl.mia.licenses.controllers;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


public class DemoController {
	//版本号变量
    private int version;
    //更新版本号接口
    @RequestMapping(value="/updateVersioin",method = RequestMethod.POST)
    public void updateVersion( @PathVariable("version") int version) {
    	ConcurrentHashMap chm;
        this.version = version;
    }
    
    //要推送给页面的数据，只是作为示例就用ModelMap，可以根据实际修改
    private ModelMap latestData;
    
    //给其他接口调用传入更新的数据（比如你有另外个controller接收子系统数据解析后并且保存到数据库成功，那么将更新的数据调用这个接口传过来
    //这里强调保存到数据库成功后才更新过来是为了保持页面展示数据跟数据库的一致
    @RequestMapping(value="/updateData",method = RequestMethod.POST)
    public void updateData( @ModelAttribute ModelMap mm) {//这里传入你要更新数据，这里的参数是随意写，你应该定义自己的参数类    	
        //这里将mm中更新的数据跟latestData整合在一起，形成最新的页面数据，这样就不需要再去查数据库了    	
    }
    
    //页面轮询接口
    @RequestMapping(value="/polling",method = RequestMethod.GET)
    public int polling(@PathVariable("version") int version) {//从页面传入版本号    	
    	int newVersion = -1;
        if(version<this.version) {
        	newVersion = this.version;//页面接收到的版本号是-1不操作，是新版本号时，就刷新数据
        }
        return newVersion;
    }    

    //推送数据给页面的接口
    @RequestMapping(value="/getData",method = RequestMethod.GET)
    public ModelMap getData() {
    	if(latestData == null) { //当latestData为null时才从数据库获取数据
    		getDataFromDB();
    	}
        return latestData;
    }    
    
    private void getDataFromDB() {
    	//get data from db and set to latestData
    }    
}
