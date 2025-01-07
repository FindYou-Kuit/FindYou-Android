package com.example.findu.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.findu.R
import com.example.findu.databinding.FragmentHomeBinding
import com.example.findu.presentation.model.HomeRv
import com.example.findu.presentation.type.AnimalStateType

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var homeProtectList: List<HomeRv>
    private lateinit var homeProtectAdapter: HomeRVAdapter
    private lateinit var homeMissingList: List<HomeRv>
    private lateinit var homeMissingAdapter: HomeRVAdapter

    private val dummyImageUrls = listOf(
        "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxMTEhUSExITFRUXFxUYFRcXGBUXFRUVFRUWFxUYFhUYHSggGBolHRUVITEhJSkrLi4uFx8zODMtNygtLysBCgoKDg0OGhAQGi0lICUtLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLS0tLf/AABEIAKgBLAMBIgACEQEDEQH/xAAcAAACAgMBAQAAAAAAAAAAAAAEBQMGAAIHAQj/xAA/EAABAwIEBAQEBAQEBQUAAAABAAIDBBEFEiExBkFRYRMicYEHkaGxMkJSwRTR4fAjYnKCJDNDkvEVNFOy0v/EABkBAAMBAQEAAAAAAAAAAAAAAAECAwQABf/EACQRAAMBAAICAgICAwAAAAAAAAABAhEDIRIxIkETUTJCBBRh/9oADAMBAAIRAxEAPwDhqxYsXHGLFi9AXHHixSeEtcqGhwxoUzWrGMUrWFK2MkeNiTGio7qCGAqw4XFbdZeXkxdDKSelwjQGyKFNkTaneAEHiMwssM3TfYzWI8hrbc0VDWm43Vea/VN6Nqq1hPdL/g2NeUApnLiNwqNRSFqaMrFDl5KzEVkZSSIWdyGdULVstysigppFLHdTUdOiY47ojwwEWzsN2Msha0iykknsllbU6boxIwgxZ+6QTOTevde6TzMXoca6IU+yBxQsiJLFE9iqugNg+VYYSp2NU7QE2ig0FISmDaEWWsbwFMaoBI2xkBVFMAoPDCnnnug3ypkdhvJGEHOFu+oQk0l1WUBkDzqp4CtYIMxR8dAQmu5XRyQM9l1AY0/jp2gIGZgupxzd4hvEr6xYsW0gYt4zqtFswLmFBJcvI23K2EKxjCCpaUGEMIsjIqQIOGVHwTLJyOiiwlFOAp45LKF02igc9SSbFp4NBXHqoZqguQcbkQ1qdQkSbJKfdOaWYBJ2aKZsqFLQJ4Pm1KJjnSGKZEsnUXA6ocmZexy6pUKlHUJuVNxiHTLDRu0U8z15RxaL2rbYLI/ZYTVdSl00l1JWtN1BGxaJWIALLFdATwp8YUBVsVYsVyJnRqCRinnNkM+RaURaB3iyhdMpZXIKVUSFJP4hbslugwEREi0FBIbdQTsREa8lS+h9FMgUJR0rEMWKyfRwTh+hTKSSwQNK6wXlTKo1PlQyeI3krChHSqCV6H8Uq0caXoV0QLFixaSJikhNio17dcFDmIiy9ICUxzEKZtSs74mVVoZNYvS+yZ8J8M1de7LBH5QbPkfpGz1O5PYXXVsK+EFGwA1Mss7rahp8KMHsG+b5uSeD+w6cYE628Rd6h+GWEg/+3cfWaYj5Z03puEMNh1ZRU9+pYHH5uuj+NCvWfOcT0S16+gqzhnDZBZ9HT+rWNY4ejmWI+arGLfCimkBdSTPidyY8+JH8/wAY9bn0SuBXJygPUiKxnBZqJ/hzss/8uxY4fqafzDt87bFaXlJhNhYlst/4hL3OUL5iu8NOGwqk6wmp1CpgqCnmEVBS8kdDy+zpdJLot6h1wkVBVGwR/wDEXXl1HZqTB6mnuhXRWTGRyDlTTowNIUsq3BH1KSVkivEgbF9UgHtRckijAutU9EWBPaoHBMHtQsjVRMmwcMUgXhKjc5MciQy2WhmUbgoiEcGCC5RuC0utmldgTwlewx3Ugjuj6SjNtkrrEBCqrisECY09xCmIS8xJ4vo5oVKaOK6OOF5WB2cEm2g5XXsNOrO19C+LXsWPbZaptNSLfCMOzzRsLHvBcLtZYvcOYaMzbntcLlaFwThXz4c8HT4i4ve9zaVhs9zhmLz+mMOBF+p5X9l1Gk+FeE2DnQzbA5XyOB97G/tdWt5jhjDImtZGwWa1osAOgQrk66HmCOkhhpImwwRsjjbs1v1J5knqVAcQLihCTISeXXVA1FfHE4XcL3sBfVZqvDQo0dGoIUb68nRKm4lnOmyx5N7p5vfQHGexwyW+5W8D3ZvK5JZ85GhVPxLiSSjnaZS9rfQ2Pulq63EgqFns6fjOGRVcXg1DL82OsC6N1tHMuCL9joeYVFb8KKm5/wCJpSzkfBGY+rMtgfcq2cN8Tw1kd2Pa8bE6ZmOOweN235FPKeqcNOafr7IuNPn7iXC300hikYAfyuALLjrl2t2sD3VemvvbTr3XTPisJDM18kLSLWDrvsR0u0j91VeGcEkrJ/AijaAbGR135GsvqXhxdfsBlN9nDcLPsRzjKzC0lwABJJ0A1J9Arxg/B9c6xFLKAebgG/PMRZdc4Y4TpMPb/hMDpCPNK+xe70/SOw+qdmsTuVXsKRziDgatA2jHbPr9rI2Hgmr5mIf7j+zVeP4xYa5T/wBbjY2sodTwxVs/6eYdWEH+qTyMIJBBBG4OhC6ka0hDVtNDUC0jBfk4aOHof2Ur/wARf1Y6t/ZyetVbrzqrvxZhb6d1gPIb5X83dieXp91SqmO6jKcvGO+xU9ascV0SH4YSywtkilZnIuWvuBqNLEA2PsmfDfwocHh9Y6MsH/TY5xLvV2lh6XWlTT+iTaKDgfDFXWH/AIeEubexeSGxtPdx39gVccP+DUp1qKpjOrYml5/7nWH0XW4mMiYGMaGNaLBo0ACHlq1ZQl7EzTnL/gtT20rJ79S2It/7bA/VKZ/gnNc5K2Fw5ZontPa9nOXVvHK1bWWTYg4cNxj4aYhTgnwRM0fmgJebdclg/wCQKp0tOQSLag2I5g9CORX1F/6mk3EGFUla200YD/yytAEjf93Mdjolc/o7D5zZBc9ABdx6D+9AOZICjsL6K28W8MTU0vhEAxHzMk0DZORcST+IX/Dy5XuSa/JRhu7if9LSfq7KhvQcJaKHMQFZaalACT4NEL8/eysFQLNWDmv5YNKEuJsFykL26p3M0lBSUpvsrTWI59gEcellNFGpY41t4avpDWbGHRP/AIf0IdXR6Xy3dz0sN9Emj2XRuAKHwIXVDhZ0ujOvh9vU/YJKrBuOXVYWrFcSDSfM2wHO91DVTZg0dgqxitaDU5LG+5Ia24H+u129NHDUgc0ZV1pBAaLm2g5BI7bk2+GPBjj+NR00LnOIDWNBPcnRotzJOgHryBI45X8UVFRI1zWN8zrMaCC/U9BqF0Ov4WbVNBqXvOubKHENzWtc23sNB0uepvNgHCMETw9kYAB8vMk9STqVSKnO12JSpPpjnhbBnCNuf8VvN27KxPoYWWL3geqDxPEBTR3yl1vxW+q4TxzxtNVTOAJawaWuQT2PptbrdU4pT6E5bfs79HidADl8aO+34hf5XQ3E/CkNZDYhrgRdjgASDyLeR9F8uyzXFrLpXwVx6fxzS+K7w7GRrSdi3Q5b7Ag6jsrVCX8SM3+yTAOD6uhqxJDO0ZdHNcDaSM7td1B+hHZdFjxUuzC2V40LTe19xqCCQmfEMDXhr22BdoOVn22v3skc1C5zM35wCPUDke6yclU6NMKfEYS0UOI03hzHzNOj2btI6X3HYpjgWE09DGY4BqbZ3nV7yOZ7dgqJhGLGNkzzyJJ5E2vr62Hurlg1UC0Su2AB15m3ddFdnXGDt0bj5nf1QjKplyLhUjjr4h+DKKWFnjTm2Zg2juLhrj+rXYbIX4eS1NVnfJGY2hxGtxc87X3HdVpUkml0JPi9TZf5Jui0jlRIw91tApGYa5MtF6Fdbi7WDVHUdQyVoc0kX2SDjbAJJIH+EbSAEt79lyrC+PKqhn8GotLFoHAZmuaDuWE21Hca2SzN6wtziO31sDJ2GGW46OB8zTyI/vVUqq4CqPEAYBI0n8YIAA/zX2PzVkjxFkzGyMeHaAhw/M0i4KZUeN6Zeeylamn2N4v6GVJTCCJrb6gDYki/a+wUkdSctzz2uqzW4qJaptPcFoBzakG4FzYhwt7XTgVA3OjRt0tyVJtNdC1LQZ4ZIuTol2I1jIxqbBVjjTj7+GY4iGV52Y0Ahrjzc5+uVg+Z5cyOXP4mxGctfna8PJHhsZJZthfdw9tSnx1OyD+LxnbocTDhoo55kswCilETTIPMQNOiYSwO6KcOs7HtLejUShB1cnmGUraqhdbTdc1xniJ0VU1kjzGBqfTuhVVuJAUrNL5XubOw084F943Fw0ePwne4vsexVZreH7AtLSD0P96pjJUx1DGlrm+IBmY7Sz2jkCrPwxjIliEcovpbUFTvj8vsO4c3ocILXJjV0OitWJ0TWPOXY6jsgpWAheZyOvPsokmimtw832WxwzsrE6EBQPe26p+RgUoobGKQxqQBEUkV3hvWy3+RkzegrhzBXTyC7T4bTeR3IAcr9T07roUkozD8rGg6fpa1tz8mi3q5DmZsUTYmCwFhbq7v1KT4tVH+HqHD9DWege+xPyH1ULryZv4eLwRVmYnmqnzutd18vYnQAeguL/zK6Dg1NcZnbrlULR4sWa/49uZsCd+mg+S6rhFcCAtCn4oXkr5DSSmBOpt17DupKKcOeSPwsGncqvY/xIyK0QPmcQCfU2snOGkNi01vqbm9z6qe9nP0T10oc05rELgnFFKGzOygW1toux4pUkg6WXIuKZmiQ3OvQan+i08Vv6I1K+ysiEk2V0+HEngVAeDqRYntzAVLM2t05wLGRG8XBGu/JXrywivE+iaGoZK10RN8wuN9HDVrtCDv0QsGIAg525ZGmzrXyutpmGYa7dVQcG4m8N3iOdla3U9T0A7ovB8YL3SPeRZ5JDrBpzHXLKR+IgWs4i9ufJuNvXqNKnFjMxkAGdrCDcg5bEO8x1s382l9Bc76I3C8QknIAJjjFrO0BPSyrFW/xZZGkal7G7b5XAkHlcW3HUHmCrzBFmAaBY9f3KVyP5egel4Eoi67Yg5xN3PeTI4km5N3X17q+0cUNNE2+WNgsBfQAdSq/QzDMIWXNtXO1tpvqmfEDmOge1x0yOH00Nloh6Qro5zxn8YHNmfDSABrCWmRwOpBsSBp0VSf8VcSY4EVAdrexY21umg191WcTow178sdtTa18vyOoS6OAu5LVqwg09PprgjjSPEadhLMsliJQPwseP0k8iLH3UGO4FC97i9jXW1NwDfuFTfg24RQyMJ1c4OG2lhawV5xQuAErSfJ+IaatPNZuXpdFuPdwrFQzwNYBZo/Exu1uoCzAsTzyuI/L9z/AEumnhx/8xt7HUjSw99dEmgEYlnLQR5Wkm43cHDQWHIH5rHnTZqVfQZw9VXqXD9QLjoLkX0udz1tsrq2lvq7XsqbwbA01EsnRscfpbM8/wD3Cv0ZHqei08cfBaQ5K+QDUMaRlyg+11HSYGwOzFoLuXZHT1IZsPN2/mpYn5Bdx1KboXWevsyw0JPX9kur8dgiJzuabb6tAHuSq5x3NJ4TpI3ljmC9wdbc/uvn/E6+aR5MjnE30vrYclTjny9k7rPR9O0nEVDUeRk0YeTYNJAJPQG9iVz74q8Ll7fEa0ZhpmI1Gt8p6eq4s6S67lwJxHJWUGSp82RpiMjr5nZbZSTzIBAv2RufHtAmt6ZzbAaqalOR4IZmzNN9Gu2NugI39ArfhGOZHFwfYF1+xvyvYpFjkRD/AA7Xab2KFpoiwlhO4078ws7evS6SSOpVtdnIPYIOWqSTDak+GGndu3oVtUVOi8+4+TC3gTPXJXJUElRmRDuemmUhPJiyOVHYbUWkaTyOnrySyBvVTALQ0Rh49LlX1Vi08tT62BP7KDFHZaB9/wAz2X9tR9bIET542no238/oPqvcUmP8A4n/AOUD3AH8ioT7PVT1Ip2JVGUxu6Ov9FeeF8TDrNJ3XPMSYXNaQNG7noTt9j8kRg9U5hBubn7Le0vFGNp6y48aYaT/AIrb3H9lV/BuNp6c+FIfEZsb7tt+k/JXfDqkTRlrrGwXP+NcCML84Byu52+h7j6j0KHGpfxYtNpahpivE0jgMrCM+1z12VJrGPLi59ySbk91cOFY2VULoHWDhs62xy+XX1H1RlVgd42lwGaxDrfqaS0/UJ5X430U8FyTpQGU5IuvRTmxPRXGjwf/AA3aatP0Oo/dJMTpvDDyQPMABfkb6kDmVVVrJPiSQHhOZ8jWkkga79Fda6URxBo0zAD/APOnbZVnhCC73vOzW/Vx/oVJjlcXuAB2ICjyLbwMfx0aYNWWbGSdXO+oBy/QgK1VnEBY0AE5iLmxAs3u46NHUn/xRZJLOjA6mw9AArJV0GdmYAE7gHa/IuHPsP7Mq/ZT0y8cL4gGxB4LQX/ms7zehOpHe1jyujMbry+J2vJcfoOIpYJHCUl4J1v+Xpb+SOxri1zoT4ZJvoDYi396p0qWIRpPsU45M1rzci/Tn8knpqtouDca32QbmOJJNyTueq18M9FoUrMJO6/R1Pgqta1tw4H0KtjMabexzHTUaDTqVw3Cat8T7i5FjcdU6dxK9/kYCL8769P79lDkit6KQ17ZdW43lkfkN2gnllc0/wCYfuvcMqM3jk9WAHTYj+qrtPSlsedxsbfLTb/T2U3D9Vdr+79PZoA+qg5xMunrLzwfV6y2sP8AEcDb/LZuvyTbiDiJ8TQ1jS4noNe/t3VM4Rrss8zL/wDUcfQGx9t1acdpBJGdSBbUt/EfTurv+OGb+2sbcNVLJRcuaXj8TQQ4tPRxFwD7pliVcNb/ADXC8M4hkoZQ4WZCLgQN/OQbE5iLki2sh32A1s28TcWxPaHB1rja+x53HVCl4IK+RHxfifkLAd7rkNfTEX106Kx8S8UMeSGNLjrc7BVKorHP3VeNV7FvxXT9mjICRddQ4J/waQxjd7i4+4A+wXN6OpAIB26q7w14bGADZdy010xZlPtBdfEWvDXZS11iN7tPraw+fNK8WbZ0Z2IdY/LqhK+tLyACSevTpda1lXctaf1D203UZXZVljpZfqNVpUvshIn2HqsqKi4+Slc9g5H6IpJioPGKxDFyTCekzXqRrkRhuHib8BeLbkgED6haSUeU/jY4djr7hUaEQTRSaEA91JxHUAUUbeZlcT6ho/og4W5SL6fyKE4ma9zYWsBd5pAABckkM5D0U5jbRs4uTpoM4aw8zU9UbXaPBt/qHik/Qj5pO6jLHHsur8McOOpaHI8f4shMkn+VxAAb7AAet1VcSw6zjonttUUntCnBcRMThfbn81b5HRVUbmSHR1rEbscPwkdCP6c1VXUPopqR74zptzCDb9oVZohrcPlopjsDux4uY5G/cHty+q6Dh9SZ6Vk7gNyx1rWDwAbabeUtPug4aNtTG9tsxLXZWn9Vjl9NbapRwpjJpi+ln8sbraOa67HNdqQNL3FwR/lCvx8n5F2Osh/8HtLGMzh1b9j/AFKEqOFmVBLZHlkbWvkc4WuGsBOl+9h80U6up2nM2Ww5jy/u4FVniLinxLwQAkuBZcX2doRa2unsO6sk0NyOPF9iXCi5kGUC2fzE8zpp7W+5QtU0N33KfT0/hwxRnV7W2cRtckm3sCG+yR1NC5xv/fzUE/m9M+fFEMk93ttyH/lX3h+qBaGlU2jw2/W6d0AdHa6FtfQV2C8bYfktIBo42d68j90Hg9IJqZ4tdzCX/wCxo82nM9u6t2Lls8fhnZwsHdHcifeyp+A1jqOez23yu8wOtu9uY5j2T8VJzn6B45Wjl/DVttlqzh/S+UroVJRXjHcfssjofLt1VEhmclrqMRF5y6Bth/qde32KD4YpfEnHRoufsPun3HrSxzW5bF19fTt/uCF4bi8Jhfzdb2aNvnc/RC68YYjluhvj01m5B6FKMGqcgNyBZwOvfp1RE7DIb8kGaJzjlAO6z+1g66Y0w2ttM+2jXOv3OXQEnqTf0t8+m4bUiSMNO/Nc/pMGOh1v/LbRO8Kq3QGx58yi6JtaVH4gUBiqnOO3lezfQHykdNwdO3dHuw5ppoZhrlcM4DdmvIa4k+uuv7JnxxF49P4oF3xXzj9UT7ZiO7SGn2KC4MxKIsNJK/yvBbrpnsLxjNfyHl7K6rygeF4sT4/wwW+Zuxuq+3CnWv8A3ouzvoL0seYebw2k35Etufuq7BhgLHC3M8kZ1IWkm9OYyUjhc20aLn30CJpq+R1mC3rrfRHcUReGctj5ra8rC9x8yEPgsAALzudvRG88dZH1WIOYzIO+59ef7oZ7HCWzuQBHo4XBRkcJe4eoTjjOhLHU81tPDbG4923yn5Ej2CzzXZUHZJ5fRRF10MKgW3UsElxf1+6FC8i6QQCLIN9rqad1ghCUmEsJnUr28/kV46nNgb6qWTEW7alTNrYso2B10+yp4iJsHgqCTYq9fD6lEk3iuH/Lb5f9T7i/sA76dFzuOe7rrqvwzpyIHSH87tPRun3ulU7SLIt1XctIVSxOj3VqqErqGXXck9l4ZSJodVjWDmU1xal5hJCTdKhmTNkMZzMNj9/VeYhUwVItLGL7Zho7p7+6jdGSND79ELJSENJ9F2foAqq+HIhtO+3QC30W1HBHELRN1OhedXH06IoxHvb7KWOjv/NM7r1pyhAzYie/qh60AA5j7N/mn0dEcpCFqMIJFsxH2KWQ0IMIq3B34fLfboFaWhrxsldDhxYS0i+v0TVlMRsbdE1CojENrt5FJ8YphHUUrpdWktL+pjbI29+umZPXOt+IEHtstq6hjq4g0+WWMHI7a4O7T8ksPK0pvReoZ43MD4yCw3tbsbELaM3C59huPVFE3w5IHyR/5baAc299vXsmcPG0WuWGovyaWdtr3sPmtaoPx/YN8TGxZIY3D/Ekkbk7AEB5Pazre46KvNpdcp6/ZHVbzUTCoqB+HSKMG4bre7jzde22nqtmXzX5lZ+Wk2khSanpAAvIsjH9T0Fgi44Hc0FU4Qx5vdzT+oboIkxxT1oO7CPqpJ2NcNFrh9I4CziH99iR3HVSVNP6hcwIHpqcuOW/bXYg7gjmqocPjixH+GBJjzxtcNvxFhcL9BmcAe3urI2oMZzG5soOIcMZU/8AEwOLJrC4GziOfrp9EeJ4ym+i64g+1wlFE3/mDuPrf+SkNd4sLJTo4tBcP0vH4x7EFLsOqvx3O9v3WrSv49RX+M8JEkZeDZzSD6jmkNLSaABWjHJdMv6j9ALpbEw8h7qHLXeErhSwnCaMZmnuFbeKqNr6cB2ug77ba8khwiMhwJ1V5noPHjAaRoNkiRCjidXg7gbNf5See4H7o+CIAAdE+x+gMbrEFJNkHT9MR037MqafRRNpR1W9RU3FggvHcl0KAWO11UzofboslkadhawWsMpdvr0VWgJGNhNxbVd64cpPBp44+jQPe2qqXw94XGlRIL/oHTur/U6J4X2MQylCS2Xsr0JJOuoZMAxGO4VZni1VqncHJLVwXP2UWuyqZHBBovJKe+inpXKRw1TA0U/wmqMpaREtj1RkUdgkaG3og/h9gFs+AAckY5ugQtQ3mbkIpYK3ooqYdfJluiYY3W8zbH5j5qWGhu/Ny5f+UyEKDOTEdRGLWsgQ0g3BTbE4LbISmjCQqiGepuPO29vZAyPbybf3TiqpxZCPpwuDiFEoJseXRMsNpfzEKX+DTampQAEEc3iImw+qX1sZYcwvfpfdP3QG29kBLhwe6xJ/dURFslw2pLh5m29x9t0wkj0U9HQMY2wACndEFTxJ6VHFaPKb6kHkEsilfGbjnuFcq2mBFlXqqhS4UTBG4kLEXOU7g8jzso4qkC9jvyCx9LqpqemC7WjRHN4rAd8Zecx9h0CMhpOqPjpgBqp44EEtfZDkrSCmhA2VkwmoylKgxbMNjurpGZvsl44pGuYHgEnt/Jc1nXXWtbNEWO106n9lyXHIfCmczv6KHLOPQaBhRZVK2HVeujSpodIUNBvbsU84IwwzzAcgdVixaAI7tSRBjQ0cgoq2TVYsT/QRVPMTshX9ysWJGOgOV6gfr87fNYsUxiAtsbrYyLFi4JNAdFMZwLXNlixKwm38U07E/T+ayeQEb/37LFi5MDJaOmAF9fmUWSAsWIgA6ixShrcr+3JYsUyiDHNuLqJkQKxYuCmEx06JAA5LFi5IVslcOq0o2+Yn+wsWKi9k69DAFeOKxYqkwWdLp2XWLEjHQtlj12U1LENFixKP9BoAXub2WLEyJs9A7rw+qxYrQQsNw+axtdU74g4QWv8AHBuCsWLuVLBZZUoag2XplWLFkzsqmf/Z",
        "data:image/jpeg;base64,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",
        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupTodayData()
        setupBanner()
        setupRV()

        return binding.root
    }

    private fun setupRV() {
        homeProtectList = listOf( // dummy 값
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "말티즈",
                type = AnimalStateType.Protect.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 화양동"
            ),
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "사모예드",
                type = AnimalStateType.Protect.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 자양동"
            ),
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "푸들",
                type = AnimalStateType.Protect.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 화양동"
            )
        )

        homeMissingList = listOf( // dummy 값
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "말티즈",
                type = AnimalStateType.Find.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 화양동"
            ),
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "사모예드",
                type = AnimalStateType.Missing.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 자양동"
            ),
            HomeRv(
                imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSEvRPU4KKmpmmg7iXY2yWqZM_vCb_KuBHTYw&s",
                name = "푸들",
                type = AnimalStateType.Find.state,
                date = "2025-01-01",
                location = "서울특별시 광진구 화양동"
            )
        )

        homeProtectAdapter = HomeRVAdapter(homeProtectList)
        homeMissingAdapter = HomeRVAdapter(homeMissingList)
        binding.rvHomeProtect.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvHomeMissing.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvHomeProtect.adapter = homeProtectAdapter
        binding.rvHomeMissing.adapter = homeMissingAdapter
        val size = resources.getDimensionPixelSize(R.dimen.MY_SIZE)
        val m_size = resources.getDimensionPixelSize(R.dimen.MY_EDGE_MARGIN)
        val deco = SpaceDecoration(size, m_size)
        binding.rvHomeProtect.addItemDecoration(deco)
        binding.rvHomeMissing.addItemDecoration(deco)
    }

    private fun setupTodayData() {
        val rescueCount = 2 // 오늘 구조된 동물 dummy 값
        binding.tvHomeTodayRescueNum.text =
            getString(R.string.home_today_bar_rescue_num, rescueCount)

        val reportCount = 6 // 오늘 신고된 동물 dummy 값
        binding.tvHomeTodayReportNum.text =
            getString(R.string.home_today_bar_report_num, reportCount)
    }

    private fun setupBanner() {
        val vpAdapter = HomeBannerAdapter(dummyImageUrls)
        binding.vpHomeBanner.adapter = vpAdapter

        val startPosition = Int.MAX_VALUE / 2
        binding.vpHomeBanner.setCurrentItem(
            startPosition - (startPosition % dummyImageUrls.size),
            false
        )

        binding.vpHomeBanner.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}