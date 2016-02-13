package com.drone.qb;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/qb")
public class QuickBookRestController {

	 @RequestMapping(method=RequestMethod.GET, value="/mins/{minutesWorked}/droneName/{droneName}")
	  public String create(@PathVariable String minutesWorked, @PathVariable String droneName) {
		 new QuickBookService().createTimeActivity(Integer.valueOf(minutesWorked), droneName, "JoAnn");
	    return "data - " + minutesWorked + " - " + droneName;
	  }
}
