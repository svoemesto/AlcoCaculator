package com.svoemestodev.alcocaculator;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Solution implements Serializable {

    private boolean isCalculatedVol;
    private boolean isCalculatedConc;
    private String name = null; // название раствора
    private Double vol = null; // объем раствора, л.
    private Double conc = null; // крепость раствора, %
    private Double ac = null; // объем АС, л.
    private Double water = null; // объем воды, л.

    transient private static final String TAG = "Solution";

    private static int checkResult(Solution result, List<Solution> ingredients) {
        int res = -3;
        if (result.ac < 0 || result.conc <0 || result.vol <0 || result.water <0) {
            res = -2;
        } else {
            for (Solution ingredient : ingredients) {
                if (ingredient.ac < 0 || ingredient.conc <0 || ingredient.vol <0 || ingredient.water <0) {
                    res = -2;
                    break;
                }
            }
        }
        return res;
    }

    public static int Mixer(Solution result, List<Solution> ingredients) {

        String logMsgPref = "Mixer: ";

        int countOfNulls = 0; // счетчик пустых значений

        if (result.vol == null) countOfNulls++;
        if (result.conc == null) countOfNulls++;

        for (Solution ingredient : ingredients) {
            if (ingredient.vol == null) countOfNulls++;
            if (ingredient.conc == null) countOfNulls++;
        }

        if (countOfNulls == 2) {

            if (result.vol != null && result.conc != null) {
                result.ac = result.getAc();
                result.water = result.getWater();
            } else {
                result.ac = null;
                result.water = null;
            }

            for (Solution ingredient : ingredients) {
                if (ingredient.vol != null && ingredient.conc != null) {
                    ingredient.ac = ingredient.getAc();
                    ingredient.water = ingredient.getWater();
                } else {
                    ingredient.ac = null;
                    ingredient.water = null;
                }
            }

            // Найдем суммы АС и воды ингредиентов, если они известны
            Double sumAc = 0.0;
            Double sumWater = 0.0;
            Double sumVol = 0.0;
            int countNullVol = 0;
            int countNullConc = 0;

            for (Solution ingredient : ingredients) {
                if (ingredient.ac != null) sumAc = sumAc + ingredient.ac;
                if (ingredient.water != null) sumWater = sumWater + ingredient.water;
                if (ingredient.vol != null) sumVol = sumVol + ingredient.vol;
                if (ingredient.vol == null) countNullVol++;
                if (ingredient.conc == null) countNullConc++;
            }

            // Вариант №1. Неизвестен объем и крепость результата.
            if (result.vol == null && result.conc == null) {
                result.ac = sumAc;
                result.water = sumWater;
                result.vol = sumAc + sumWater;
                result.conc = sumAc * 100 / result.vol;
                return checkResult(result, ingredients);
            }

            // Вариант №2. Неизвестен объем и крепость какого-то одного ингредиента.
            for (Solution ingredient : ingredients) {
                if (ingredient.vol == null && ingredient.conc == null) {
                    ingredient.ac = result.ac - sumAc;
                    ingredient.water = result.water - sumWater;
                    ingredient.vol = ingredient.ac + ingredient.water;
                    ingredient.conc = ingredient.ac * 100 / ingredient.vol;
                    return checkResult(result, ingredients);
                }
            }


            // Вариант №3. Неизвестна крепость результата и объем одного из компонентов
            if (result.conc == null) {
                for (Solution ingredient : ingredients) {
                    if (ingredient.vol == null) {

                        ingredient.vol = result.vol - (sumAc+sumWater);
                        ingredient.ac = ingredient.vol * ingredient.conc / 100;
                        ingredient.water = ingredient.vol - ingredient.ac;

                        sumAc = sumAc + ingredient.ac;
                        sumWater = sumWater + ingredient.water;

                        result.ac = sumAc;
                        result.water = sumWater;
                        result.conc = result.ac * 100 / result.vol;

                        return checkResult(result, ingredients);

                    }
                }
            }

            // Вариант №4. Неизвестен объем результата и концентрация одного из компонентов
            if (result.vol == null) {
                for (Solution ingredient : ingredients) {
                    if (ingredient.conc == null) {

                        result.vol = sumVol;
                        result.ac = result.vol * result.conc / 100;
                        result.water = result.vol - result.ac;

                        ingredient.ac = result.ac - sumAc;
                        ingredient.water = ingredient.vol - ingredient.ac;
                        ingredient.conc = ingredient.ac * 100 / ingredient.vol;

                        return checkResult(result, ingredients);

                    }
                }
            }

            // Вариант №5. Неизвестен объем результата и объем одного из компонентов
            if (result.vol == null) {
                for (Solution ingredient : ingredients) {
                    if (ingredient.vol == null) {

                        result.vol = ((sumAc+sumWater)*ingredient.conc - sumAc*100)/(ingredient.conc - result.conc);
                        result.ac = result.vol * result.conc / 100;
                        result.water = result.vol - result.ac;

                        ingredient.vol = result.vol - (sumAc + sumWater);
                        ingredient.ac = ingredient.vol * ingredient.conc / 100;
                        ingredient.water = ingredient.vol - ingredient.ac;

                        return checkResult(result, ingredients);

                    }
                }
            }

            // Вариант №6. Неизвестны объемы двух компонентов.
            if (countNullVol == 2) {

                Double tmpAc = result.ac - sumAc;
                Double tmpWater = result.water - sumWater;
                Double tmpVol = tmpAc + tmpWater;

                List<Solution> ings = new ArrayList<>();
                for (Solution ingredient : ingredients) {
                    if (ingredient.vol == null) {
                        ings.add(ingredient);
                    }
                }
                ings.get(1).vol = (tmpVol*ings.get(0).conc - tmpAc*100) / (ings.get(0).conc - ings.get(1).conc);
                ings.get(1).ac = ings.get(1).vol * ings.get(1).conc / 100;
                ings.get(1).water = ings.get(1).vol - ings.get(1).ac;

                ings.get(0).vol = tmpVol - ings.get(1).vol;
                ings.get(0).ac = ings.get(0).vol * ings.get(0).conc / 100;
                ings.get(0).water = ings.get(0).vol - ings.get(1).ac;

                return checkResult(result, ingredients);

            }

            // Вариант №7. Неизвестны объем одного компонента и крепость другого компонента
            if (countNullConc == 1 && countNullVol == 1) {
                for (Solution ingredient : ingredients) {
                    if (ingredient.vol == null) {
                        ingredient.vol = result.vol - sumVol;
                        ingredient.ac = ingredient.vol * ingredient.conc / 100;
                        ingredient.water = ingredient.vol - ingredient.ac;
                        sumAc = sumAc + ingredient.ac;
                        sumWater = sumWater + ingredient.water;
                        break;
                    }
                }
                for (Solution ingredient : ingredients) {
                    if (ingredient.conc == null) {
                        ingredient.ac = result.ac - sumAc;
                        ingredient.water = result.water - sumWater;
                        ingredient.conc = ingredient.ac * 100 / ingredient.vol;
                        break;
                    }
                }
                return checkResult(result, ingredients);
            }

            // Вариант №8. Неизвестны крепость результата и крепость одного из компонентов
            if (result.conc == null) {
                for (Solution ingredient : ingredients) {
                    if (ingredient.conc == null) {
                        Log.e(TAG, logMsgPref + "Невозможно вычислить две концентрации.");
                        return -1;
                    }
                }
            }

            // Вариант №9. Неизвестны крепость двух компонентов
            if (countNullConc == 2) {
                Log.e(TAG, logMsgPref + "Невозможно вычислить две концентрации.");
                return -1;
            }

            return 0;
        } else {
            Log.e(TAG, logMsgPref + "Количество неизвестных переменных для смешивания должно быть 2. А оно равно " + countOfNulls + ". Миксер не может произвести вычисления.");
            return countOfNulls;
        }
    }


    public Solution() {
    }

    @Override
    public String toString() {
        return "Раствор { Объём = " + vol + "л., концентрация = " + conc + "%, (Спирт: " + getAc() + ", Вода: " + getWater() + ") }";
    }

    public Solution(boolean isCalculatedVol, boolean isCalculatedConc, String name, Double vol, Double conc) {
        this.isCalculatedVol = isCalculatedVol;
        this.isCalculatedConc = isCalculatedConc;
        this.name = name;
        this.vol = vol;
        this.conc = conc;
    }

    public Solution(Solution... solutions) {
        double tempVol = 0;
        double tempAc = 0;
        for (Solution solution : solutions) {
            tempVol = tempVol + solution.getVol();
            tempAc = tempAc + solution.getAc();
        }
        this.vol = tempVol;
        this.conc = tempAc * 100 / tempVol;
    }

    public boolean isCalculatedVol() {
        return isCalculatedVol;
    }

    public void setCalculatedVol(boolean calculatedVol) {
        isCalculatedVol = calculatedVol;
    }

    public boolean isCalculatedConc() {
        return isCalculatedConc;
    }

    public void setCalculatedConc(boolean calculatedConc) {
        isCalculatedConc = calculatedConc;
    }

    // получаем объем АС в растворе
    public Double getAc() {
        return vol*conc/100;
    }

    // получаем объем воды в растворе
    public Double getWater() {
        return vol*(1 - conc/100);
    }

    // получаем объем раствора
    public Double getVol() {
        return vol;
    }

    // задаем объем раствора
    public void setVol(Double vol) {
        this.vol = vol;
    }

    // получаем концентрацию раствора
    public Double getConc() {
        return conc;
    }

    // задаем концентрацию раствора
    public void setConc(Double conc) {
        this.conc = conc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
