package org.example.CollectorA.PinterestSearchEngine;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.springframework.stereotype.Component;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.parser.Parser;

@Component
public class PinterestUserPageParserImpl implements PinterestUserPageParser {
    private Document page;

    @Override
    public void loadPage(Document page) {
        this.page = page;
    }

    @Override
    public String getUsername() {
        Element el = page.selectFirst("div[data-test-id=\"creator-profile-name\"]");
        return el == null ? "" : el.text();
    }
    // <div class="FNs zI7 iyn Hsu">Nino Japaridze</div>

    @Override
    public long getFollowersAmount() {
        String followers = page.selectFirst("div[data-test-id=\"follower-count\"]").text();
        System.out.println("followers: " + followers);
        long amount = 0;
        int numbers[] = {0,0};
        Matcher matcher = Pattern.compile("[0-9]+").matcher(followers);
        if (matcher.find()) {
            numbers[0] = Integer.valueOf(followers.substring(matcher.start(), matcher.end()));
        }
        if (matcher.find()) {
            int start = matcher.start();
            numbers[1] = Integer.valueOf(followers.substring(start, start + 1));
        }

//        System.out.println("numbers: " + numbers[0] + " " + numbers[1]);
        if (Pattern.matches(".*тыс.*", followers)) {
            amount = 1000 * numbers[0] + 100 * numbers[1];
        } else if (Pattern.matches(".*млн.*", followers)
                || Pattern.matches(".*[0-9]M.*", followers)) {
//            System.out.println("Million!");
            amount = 1_000_000 * numbers[0] + 100_000 * numbers[1];
        } else {
            amount = numbers[0];
        }

        return amount;
    }
    //<div class="X8m zDA IZT tBJ dyH iFc sAJ H2s">11,9&nbsp;тыс. подписчика</div>

    @Override
    public long getFollowingsAmount() {
        return 0;
    }
    //<div class="X8m zDA IZT tBJ dyH iFc sAJ H2s">83,6&nbsp;тыс. подписок</div>

    @Override
    public List<String> getPins(int pinsNumber) {
        return null;
    }
    //<img alt="" class="hCL kVc L4E MIw" fetchpriority="auto" loading="auto" src="https://i.pinimg.com/236x/0a/e1/c7/0ae1c77b64581b131c776b2f3849fefd.jpg">
}
