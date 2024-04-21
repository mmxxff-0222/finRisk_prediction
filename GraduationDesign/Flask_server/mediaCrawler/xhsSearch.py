

import config
from base.base_crawler import AbstractCrawler
from media_platform.weibo import WeiboCrawler
from media_platform.xhs import XiaoHongShuCrawler

from multiprocessing import cpu_count
from sanic import Sanic
from sanic.response import json
from sanic_limiter import Limiter, get_remote_address, RateLimitExceeded


app = Sanic(__name__)
limiter = Limiter(app, key_func=get_remote_address, strategy="moving-window")

class CrawlerFactory:
    CRAWLERS = {
        "xhs": XiaoHongShuCrawler,
        "wb": WeiboCrawler
    }

    @staticmethod
    def create_crawler(platform: str) -> AbstractCrawler:
        crawler_class = CrawlerFactory.CRAWLERS.get(platform)
        if not crawler_class:
            raise ValueError("Invalid Media Platform Currently only supported xhs or wb")
        return crawler_class()

    async def preSet(self, platform, lt, ctype):
        crawler = CrawlerFactory.create_crawler(platform=platform)
        crawler.init_config(platform=platform, login_type=lt, crawler_type=ctype)
        await crawler.start()

    async def search999(self, creator_id):
        config.XHS_CREATOR_ID_LIST = [creator_id]
        print(config.XHS_CREATOR_ID_LIST)
        try:
            await self.preSet(config.PLATFORM, config.LOGIN_TYPE, config.CRAWLER_TYPE)
        except KeyboardInterrupt:
            return "error"




@app.exception(RateLimitExceeded)
def handle_over_rate_limit(request, exception):
    return json({"status": -1, "message": f"RateLimitExceeded({exception},{request})"}, status=429)



def returns(request, msg="参数错误"):
    if "成功" in msg:
        status = 1
    else:
        status = 2
    return json({
        "url": request.url,
        "query_string": request.query_string,
        "args": request.args,
        "status":str(status),
        "status_msg": str(msg),
        "task_status": "2",
    })

@app.route("/mt_price_info")
@limiter.limit("10000/second;10000/minute")
async def mt_price_info(request):
    creator_id = request.args.get('creator_id')
    if not creator_id:
        return returns({'error': 'Creator ID is required'}), 400
    else:
        # 调用异步函数并将协程对象转换为响应对象
        factory = CrawlerFactory()
        await factory.search999(str(creator_id).replace("\"", ""))

    return returns(request)


if __name__ == '__main__':
    app.run(port=9980, debug=False, auto_reload=True, workers=cpu_count())


