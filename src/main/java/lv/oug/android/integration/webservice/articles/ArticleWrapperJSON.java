package lv.oug.android.integration.webservice.articles;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleWrapperJSON {

    @SerializedName("articles")
    private List<ArticleJSON> articles;

    public List<ArticleJSON> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleJSON> articles) {
        this.articles = articles;
    }
}
