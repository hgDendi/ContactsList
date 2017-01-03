/**
 * Copyright 2017 ChenHao Dendi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hgdendi.contactslist;

import com.hgdendi.contactslist.common.Abbreviated;
import com.hgdendi.contactslist.common.ContactsUtils;

class ShareContactsBean implements Abbreviated, Comparable<ShareContactsBean> {
    private final String mName;
    private final String mPhone;
    private final String mAbbreviation;
    private final String mInitial;

    ShareContactsBean(String name, String phone) {
        this.mName = name;
        this.mPhone = phone;
        this.mAbbreviation = ContactsUtils.getAbbreviation(name);
        this.mInitial = mAbbreviation.substring(0, 1);
    }

    @Override
    public String getInitial() {
        return mInitial;
    }

    public String getName() {
        return mName;
    }

    public String getPhone() {
        return mPhone;
    }

    @Override
    public int compareTo(ShareContactsBean r) {
        if (mAbbreviation.equals(r.mAbbreviation)) {
            return 0;
        }
        boolean flag;
        if ((flag = mAbbreviation.startsWith("#")) ^ r.mAbbreviation.startsWith("#")) {
            return flag ? 1 : -1;
        }
        return getInitial().compareTo(r.getInitial());
    }
}