package task.utils;

public class ToolsForWord {

    public String getWordWithACapitalLetter(String name){
        if(name.charAt(0)>='A' && name.charAt(0)<='Я'){
            return name;
        }

//        char c = name.charAt(0);

        return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1, name.length());
    }
}
