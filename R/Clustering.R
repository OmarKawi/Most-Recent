
#ClothingID_age_type <- read.csv(file = "Womens Clothing E-Commerce Reviews.csv", sep = " ")[ ,c('Age', 'Department Name')]
rm(list=ls())
Readdata <- read.csv("Womens Clothing E-Commerce Reviews.csv", na.strings = "No comment!")
Readdata[1] <- list(NULL)
Readdata[3:4] <- list(NULL)
Readdata[4:8] <- list(NULL)

comments_sorted <- Readdata[order(Readdata$Clothing.ID),]
comments_sorted$Clothing.ID <- as.numeric(comments_sorted$Clothing.ID)


comments_new <- ddply(Readdata,c("Clothing.ID","Rating"), 
                     function(df1)paste(df1$Rating, 
                                        collapse = ","))

comments_new$Clothing.ID <- NULL
comments_new$Rating <- NULL

#Rename column headers for ease of use
colnames(comments_new) <- c("ClothingandAge")
write.csv(comments_new,"ClothingandAge.csv",quote = FALSE,row.names = TRUE)
library(arules)
txn = read.transactions(file="ClothingandAge.csv", rm.duplicates= TRUE, format="basket",sep=",",cols=1)
txn@itemInfo$labels <- gsub("\"","",txn@itemInfo$labels)
basket_rules <- apriori(txn,parameter = list(sup = 0.01, conf = 0.5,target="rules"))
if(sessionInfo()['basePkgs']=="tm" | sessionInfo()['otherPkgs']=="tm"){
  detach(package:tm, unload=TRUE)
}

inspect(basket_rules)
df_basket <- as(basket_rules,"data.frame")
View(df_basket)
