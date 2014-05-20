
from scrapy.spider import Spider
from scrapy.selector import Selector
from bdproject.items import BdProjectItem
class TvShowsSpider(Spider):
    name = "tvshows"
    allowed_domains = ["tvsubtitles.net"]
    start_urls = [
        "http://www.tvsubtitles.net/tvshows.html",
    ]

    def parse(self, response):
        sel = Selector(response)
	tvshows = sel.xpath('/html/body/div[2]/div[4]/div/table/tr/td/table/tr/td/table/tr')
	items = []
        for tvshow in tvshows:
            item = BdProjectItem()
            item['link']= tvshow.xpath('td[2]/a/@href').extract()
            item ['nbSeasons']= tvshow.xpath('td[3]/text()').extract()
            item['title'] = tvshow.xpath('td[2]/a/b/text()').extract()
            items.append(item)
	return items
