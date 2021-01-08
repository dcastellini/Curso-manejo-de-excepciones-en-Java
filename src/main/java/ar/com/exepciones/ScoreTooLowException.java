package ar.com.exepciones;

public class ScoreTooLowException extends  Exception{

    private String name;
    private int score;

    public ScoreTooLowException(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;
    }

    @Override
    public String getMessage(){
        return "El usuario "+ name +" tiene menos puntos (" + score + ") de los requeridos.";
    }

}
