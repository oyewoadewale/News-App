package com.example.hp.newsflash;


/**
 * An {@link News} object contains information related to a single News info.
 */


public class News {

    // Here i declare all global variable private, but use public method to get them later
    private String mTitle;
    private String mAuthor;
    private String mSectionName;
    private String mDate;
    private String mUrl;


    /**
     * Constructs a new {@link News} object.
     *  @param Title is the title/ news headlines
     * @param SectionName is the different section of news
     * @param Date is the date and time in milliseconds
     * @param Url is the website URL to find more details about the news
     */

    public News(String Title, String SectionName, String Date, String Url, String Author) {
        mTitle = Title;
        mSectionName = SectionName;
        mDate = Date;
        mUrl = Url;
        mAuthor = Author;
    }

    /**
     * Returns the Title of the feed news.
     */
    public String getTitle() {
        return mTitle;
    }


    /**
     * Returns the SectionName of the feed news.
     */
    public String getSectionName() {
        return mSectionName;
    }


    /**
     * Returns the date of the feed news.
     */
    public String getDate() {

        return mDate;
    }

    /**
     * Returns the URL of the feed news.
     */
    public String getUrl() {
        return mUrl;
    }

    /**
     * Returns the Author name of the feed news.
     */
    public String getAuthor() {
        return mAuthor;
    }
}