package repository

import models.DataModel

trait AbstractRepository {

    def getFilePath: String
    def getAll: List[DataModel]
    def updateAll(string: String)
}
