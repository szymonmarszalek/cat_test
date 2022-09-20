package pl.softwarelytest.ViewModels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.Observable
import kotlinx.coroutines.launch
import pl.softwarelytest.Repositories.CatsRepository
import pl.softwarelytest.dto.CatAgeModelDto
import pl.softwarelytest.dto.CatDescriptionDto
import pl.softwarelytest.dto.CatModel

class MainViewModel(

): ViewModel() {
    private val catsRepository = CatsRepository()
    fun getCatModelList(context: Context): MutableLiveData<List<CatModel>> {
        var catListLiveData = MutableLiveData<List<CatModel>>()

            val catsBasicData = catsRepository.getCats(context).blockingGet()
            val catsAgesData = catsRepository.getCatsAges(context).blockingGet()
            val catsDescriptionData = catsRepository.getCatsDescription(context).blockingGet()

            val catsBasicDataObservable = Observable.fromIterable(catsBasicData)
            val catsAgesDataObservable: Observable<CatAgeModelDto?> = Observable.fromIterable(catsAgesData)
            val catsDescriptionDataObservable: Observable<CatDescriptionDto?> = Observable.fromIterable(catsDescriptionData)

            var catModelArray = arrayListOf<CatModel>()

            Observable
                .zip(
                    catsBasicDataObservable,
                    catsAgesDataObservable,
                    catsDescriptionDataObservable
                ) { catBasic, catAge, catDescription ->
                    CatModel(
                        catBasic.id,
                        catBasic.name,
                        catBasic.imageUrl,
                        catDescription.description,
                        catAge.age
                    )
                }
                .subscribe {
                    catModelArray.add(it)
                }

            catListLiveData.postValue(catModelArray)

        return catListLiveData
    }
}