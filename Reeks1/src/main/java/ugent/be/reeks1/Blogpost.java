package ugent.be.reeks1;

public class Blogpost {

    private String title;
    private String content;

    public  Blogpost(){}
    public  Blogpost(String t, String c){title = t; content = c;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
