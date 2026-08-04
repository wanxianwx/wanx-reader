package com.wanx.reader.data.repository

import com.wanx.reader.domain.model.Book
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 书籍数据仓库
 *
 * 职责：封装所有书籍数据访问（本地数据库 + 网络请求），
 *      对外暴露 Flow 和 suspend 函数。
 *
 * 对应 legado 的 BookRepository + BookDao + RemoteBookRepository。
 * 当前阶段使用内存模拟数据；阶段 4 后可替换为 Room DAO。
 */
@Singleton
class BookRepository @Inject constructor() {

    /* ── 内存模拟数据（后续替换为 Room DAO）── */
    private val _books = MutableStateFlow(generateMockBooks())

    /** 获取所有书架书籍的 Flow */
    fun observeBookshelf(): Flow<List<Book>> = _books.asStateFlow()

    /** 获取单本书 */
    fun observeBook(bookUrl: String): Flow<Book?> =
        _books.map { list -> list.find { it.bookUrl == bookUrl } }

    /** 刷新书架（从网络同步） */
    suspend fun refreshBookshelf(): Result<Unit> {
        return try {
            /* 模拟网络延迟 */
            delay(1500)
            // TODO: 阶段 4 替换为实际网络请求
            // val remoteBooks = apiService.fetchBookshelf()
            // bookDao.upsertAll(remoteBooks)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /** 添加书籍到书架 */
    suspend fun addBook(book: Book) {
        _books.value = _books.value + book
    }

    /** 从书架移除书籍 */
    suspend fun removeBook(bookUrl: String) {
        _books.value = _books.value.filter { it.bookUrl != bookUrl }
    }

    /** 更新书籍阅读进度 */
    suspend fun updateProgress(bookUrl: String, chapterIndex: Int, chapterPos: Int) {
        _books.value = _books.value.map { book ->
            if (book.bookUrl == bookUrl) {
                book.copy(
                    durChapterIndex = chapterIndex,
                    durChapterPos = chapterPos,
                )
            } else book
        }
    }
}

/* ── 模拟数据生成 ── */

private fun generateMockBooks(): List<Book> = listOf(
    Book(
        id = 1L, name = "三体", author = "刘慈欣",
        coverUrl = "https://img2.doubanio.com/view/subject/l/public/s2768378.jpg",
        kind = "科幻", intro = "文化大革命如火如荼进行的同时...",
        origin = "legado", originName = "起点中文网",
        latestChapterTitle = "第 36 章 尾声", durChapterIndex = 12,
        totalChapterNum = 36, durChapterPos = 0, bookUrl = "book://1",
        canUpdate = true, groupId = 0, order = 0,
        lastCheckTime = System.currentTimeMillis(), lastCheckCount = 0,
        isInShelf = true
    ),
    Book(
        id = 2L, name = "百年孤独", author = "加西亚·马尔克斯",
        coverUrl = "https://img2.doubanio.com/view/subject/l/public/s6384944.jpg",
        kind = "文学", intro = "《百年孤独》是魔幻现实主义文学的代表作...",
        origin = "legado", originName = "豆瓣阅读",
        latestChapterTitle = "第二十章", durChapterIndex = 20,
        totalChapterNum = 20, durChapterPos = 45, bookUrl = "book://2",
        canUpdate = false, groupId = 0, order = 1,
        lastCheckTime = System.currentTimeMillis(), lastCheckCount = 0,
        isInShelf = true
    ),
    Book(
        id = 3L, name = "1984", author = "乔治·奥威尔",
        coverUrl = "https://img2.doubanio.com/view/subject/l/public/s4371408.jpg",
        kind = "政治", intro = "《1984》是一部杰出的政治寓言小说...",
        origin = "legado", originName = "微信读书",
        latestChapterTitle = "第三部 第 6 节", durChapterIndex = 24,
        totalChapterNum = 24, durChapterPos = 0, bookUrl = "book://3",
        canUpdate = true, groupId = 0, order = 2,
        lastCheckTime = System.currentTimeMillis(), lastCheckCount = 0,
        isInShelf = true
    ),
    Book(
        id = 4L, name = "活着", author = "余华",
        coverUrl = "https://img2.doubanio.com/view/subject/l/public/s29053580.jpg",
        kind = "文学", intro = "地主少爷富贵嗜赌成性...",
        origin = "local", originName = "本地导入",
        latestChapterTitle = "全文完", durChapterIndex = 10,
        totalChapterNum = 10, durChapterPos = 100, bookUrl = "book://4",
        canUpdate = false, groupId = 0, order = 3,
        lastCheckTime = System.currentTimeMillis(), lastCheckCount = 0,
        isInShelf = true
    ),
    Book(
        id = 5L, name = "人类简史", author = "尤瓦尔·赫拉利",
        coverUrl = "https://img2.doubanio.com/view/subject/l/public/s27814883.jpg",
        kind = "历史", intro = "十万年前，地球上至少有六种不同的人...",
        origin = "legado", originName = "得到",
        latestChapterTitle = "第二十章 智人末日", durChapterIndex = 0,
        totalChapterNum = 20, durChapterPos = 0, bookUrl = "book://5",
        canUpdate = true, groupId = 0, order = 4,
        lastCheckTime = System.currentTimeMillis(), lastCheckCount = 0,
        isInShelf = true
    ),
    Book(
        id = 6L, name = "月亮与六便士", author = "毛姆",
        coverUrl = "https://img2.doubanio.com/view/subject/l/public/s29651121.jpg",
        kind = "文学", intro = "一个英国证券交易所的经纪人...",
        origin = "legado", originName = "蜗牛读书",
        latestChapterTitle = "第五十八章", durChapterIndex = 58,
        totalChapterNum = 58, durChapterPos = 20, bookUrl = "book://6",
        canUpdate = false, groupId = 0, order = 5,
        lastCheckTime = System.currentTimeMillis(), lastCheckCount = 0,
        isInShelf = true
    ),
)