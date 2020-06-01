package com.svoemestodev.alcocaculator;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Receipt implements Serializable {
    private String name;
    private Solution result;
    private List<Solution> ingredients;

    public Receipt(String name, Solution result, List<Solution> ingredients) {
        this.name = name;
        this.result = result;
        this.ingredients = ingredients;
    }

    public Receipt() {
    }

    public static List<Receipt> getDefaultList() {
        Solution solSpiritus = new Solution(true, true, "C₂H₅OH",2.0,96.4);
        Solution solWater = new Solution(true, true, "H₂O",3.0,0.0);
        Solution solVodka = new Solution(false, false, "Vodka",null,null);

        List<Solution> ingredients = new ArrayList<>();
        ingredients.add(solSpiritus);
        ingredients.add(solWater);

        Receipt receipt = new Receipt("Vodka", solVodka, ingredients);

        List<Receipt> list = new ArrayList<>();
        list.add(receipt);
        return list;
    }

    public static List<Receipt> loadList(String pathToFile) {
        List<Receipt> list = new ArrayList<>();
        File file = new File(pathToFile);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fileInputStream);
            list = (List<Receipt>) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            Log.e("Receipt", "loadList. Ошибка десериализации. Возвращаем список по-умолчанию.");
            list = getDefaultList();
        }
        return list;
    }

    public static boolean saveList(List<Receipt> list, String pathToFile) {
        File file = new File(pathToFile);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fileOutputStream);
            oos.writeObject(list);
            oos.close();
            return true;
        } catch (IOException e) {
            Log.e("Receipt", "saveList. Ошибка сериализации");
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Solution getResult() {
        return result;
    }

    public void setResult(Solution result) {
        this.result = result;
    }

    public List<Solution> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Solution> ingredients) {
        this.ingredients = ingredients;
    }
}
