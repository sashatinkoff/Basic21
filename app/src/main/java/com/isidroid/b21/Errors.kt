package com.isidroid.b21

class SubredditsException(message: String? = null) : Throwable(message)
class SubredditsTrendingException(message: String? = null) : Throwable(message)
class OAuthTokenException(message: String? = null) : Throwable(message)
class AuthException(message: String? = null) : Throwable(message)
class UserInfoException(message: String? = null) : Throwable(message)
class RefreshTokenException(message: String? = null) : Throwable(message)
class SubredditNoFoundException(message: String? = null) : Throwable(message)
class PostsException(message: String? = null) : Throwable(message)
class VoteException(message: String? = null) : Throwable(message)
class SaveException(message: String? = null) : Throwable(message)
class CommentsLoadingException(message: String? = null) : Throwable(message)
class CommentsMoreException(message: String? = null) : Throwable(message)
class SubmitCommentException(message: String? = null, val retryAvailable: Boolean = false) :
    Throwable(message)

class IllegalSearchException(message: String? = null) : Throwable(message)
class SubredditDataException(message: String? = null) : Throwable(message)
class SubredditJoinException(message: String? = null) : Throwable(message)
class DownloadFileException(message: String? = null) : Throwable(message)
class SubredditSearchException(message: String? = null) : Throwable(message)
class UpdateCustomFeedException(message: String? = null) : Throwable(message)
class CustomFeedFormException(message: String? = null) : Throwable(message)
class CustomFeedDeleteException(message: String? = null) : Throwable(message)
class LoadMessagesException(message: String? = null) : Throwable(message)
class ImagesNotSelectedException(message: String? = null) : Throwable(message)
class ImgurUploadException(message: String? = null) : Throwable(message)
class UnknownPostKindException(message: String? = null) : Throwable(message)
class SubmitPostException(message: String? = null) : Throwable(message)