import argparse
import asyncio
import sys

import config
from base.base_crawler import AbstractCrawler
from media_platform.weibo import WeiboCrawler
from media_platform.xhs import XiaoHongShuCrawler


class CrawlerFactory:
    CRAWLERS = {
        "xhs": XiaoHongShuCrawler,
        "wb": WeiboCrawler
    }

    @staticmethod
    def create_crawler(platform: str) -> AbstractCrawler:
        crawler_class = CrawlerFactory.CRAWLERS.get(platform)
        if not crawler_class:
            raise ValueError("Invalid Media Platform Currently only supported xhs or dy or ks or bili ...")
        return crawler_class()


async def main():
    # define command line params ...
    parser = argparse.ArgumentParser(description='Media crawler program.')
    parser.add_argument('--platform', type=str, help='Media platform select (xhs | dy | ks | bili | wb)',
                        choices=["xhs", "dy", "ks", "bili", "wb"], default=config.PLATFORM)
    parser.add_argument('--lt', type=str, help='Login type (qrcode | phone | cookie)',
                        choices=["qrcode", "phone", "cookie"], default=config.LOGIN_TYPE)
    parser.add_argument('--type', type=str, help='crawler type (search | detail | creator)',
                        choices=["search", "detail", "creator"], default=config.CRAWLER_TYPE)


    args = parser.parse_args()
    crawler = CrawlerFactory.create_crawler(platform=args.platform)
    crawler.init_config(
        platform=args.platform,
        login_type=args.lt,
        crawler_type=args.type
    )
    await crawler.start()



def search999():
    try:
        # asyncio.run(main())
        asyncio.get_event_loop().run_until_complete(main())
    except KeyboardInterrupt:
        sys.exit()
