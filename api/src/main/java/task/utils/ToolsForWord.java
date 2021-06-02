package task.utils;

public class ToolsForWord {

    public String getWordWithACapitalLetter(String name){
        if((name.charAt(0)>='A' && name.charAt(0)<='Я')||(name.charAt(0)>='A' && name.charAt(0)<='Z')){
            return name;
        }

        return String.valueOf(name.charAt(0)).toUpperCase() + name.substring(1, name.length());
    }
}
