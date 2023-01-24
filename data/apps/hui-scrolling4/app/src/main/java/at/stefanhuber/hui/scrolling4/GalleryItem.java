package at.stefanhuber.hui.scrolling4;

public class GalleryItem {

    protected String title;
    protected String subtitle;
    protected String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toJson() {
        String title = "\"title\": \"" + getTitle() + "\",";
        String subtitle = "\"subtitle\": \"" + getSubtitle() + "\",";
        String image = "\"image\": \"" + getImage() + "\"";
        return "{" + title + subtitle + image + "}";
    }

}
