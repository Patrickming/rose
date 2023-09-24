package com.bc3.rose.dto;


import com.bc3.rose.entity.Setmeal;
import com.bc3.rose.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
