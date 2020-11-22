using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.Monetization;

public class AdsManager : MonoBehaviour
{
    public Text rewardText;
    private int rewardCount=0;
  string placementId_video = "video";
  string placementId_rewardedVideo = "rewardedVideo";
 private string gameId="1234567";

 private bool testMode=true;

    // Start is called before the first frame update
    void Start()
    {
// if(Application.platform==RuntimePlatform.IphonePlayer)

   // gameId="3901590";

 if (Application.platform==RuntimePlatform.Android)

    gameId="3901591";
        
        Monetization.Initialize(gameId,testMode);
    }
 

    public void ShowAd()
{
    StartCoroutine(WaitForAd());
}
public void ShowRewardedAd()
{
    StartCoroutine(WaitForAd(true));
}

IEnumerator WaitForAd(bool reward=false)
{
    string placementId = reward ? placementId_rewardedVideo : placementId_video;
    while (!Monetization.IsReady(placementId))
    {
        yield return null;
    }
    ShowAdPlacementContent ad = null;
    ad = Monetization.GetPlacementContent(placementId) as ShowAdPlacementContent;

if(ad!=null)
{
    if(reward)
       ad.Show(AdFinished);
    else
     ad.Show();
}
}

void AdFinished(ShowResult result)
{
    if (result == ShowResult.Finished)
    {
        rewardCount++;
        rewardText.text=rewardCount.ToString();
    }
}

}
