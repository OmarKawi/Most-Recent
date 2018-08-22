lower_rate <-comments[comments$Rating < 3, ]
lower_rate_titiles <-lower_rate[,5] 

docs2 <- Corpus(VectorSource(lower_rate_titiles))
docs2 <- tm_map(docs2, content_transformer(tolower))
docs2 <- tm_map(docs2, removeNumbers)
docs2 <- tm_map(docs2, removeWords, stopwords("english"))
docs2 <- tm_map(docs2, stemDocument)
dtm2 <- TermDocumentMatrix(docs2)
m2 <- as.matrix(dtm2)
v2 <- sort(rowSums(m2),decreasing=TRUE)
d2 <- data.frame(word = names(v2),freq=v2)
head(d2, 10)
par(bg="grey30")
png(file="TextMiningforLowerRateComments.png",width=1000,height=700, bg="grey30")
wordcloud(d2$word, d2$freq, col=terrain.colors(length(d2$word), alpha=0.9), random.order=FALSE, rot.per=0.3 )
title(main = "Top Comments in lower rating", font.main = 3, col.main = "cornsilk3", cex.main = 1.5)
dev.off()

